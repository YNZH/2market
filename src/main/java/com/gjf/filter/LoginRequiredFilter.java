package com.gjf.filter;

import com.gjf.config.UrlProperties;
import com.gjf.exception.ExceptionEnum;
import com.gjf.exception.GlobalException;
import com.gjf.model.ResultBean;
import com.gjf.utils.JwtKit;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jdk.nashorn.internal.parser.JSONParser;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import springfox.documentation.spring.web.json.Json;

/**
 * 过滤用户(未登录)的url请求 可自行配置无需过滤的url白名单
 *
 * @Author: GJF
 * @Date : 2018/04/23
 * Time   : 16:21
 */
@WebFilter(urlPatterns = "/api/*",asyncSupported = true)
public class LoginRequiredFilter implements Filter {

    private String excludedUrls;
    private HttpServletRequest rq;
    private Optional<String> optional;
    @Autowired
    private UrlProperties urlProperties;
    private Logger logger = LoggerFactory.getLogger(LoginRequiredFilter.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludedUrls = urlProperties.getExcluded();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        rq = (HttpServletRequest) request;
        if (excludedUrls != null && excludedUrls.length() > 0) {
            optional = Arrays.stream(excludedUrls.split(urlProperties.getSeparator()))
                    .filter(x -> x.equalsIgnoreCase(rq.getServletPath()))
                    .findFirst();
        }
        if (!optional.isPresent()) {
            logger.info("=========================url:" + rq.getServletPath() + "进入过滤器需要验证token=====================");
            String accessToken = JwtKit.getTokenFromRequest((HttpServletRequest) request);
            logger.info("token======>"+accessToken);
            if (accessToken != null && accessToken.length() > 0) {
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                try {
                    Long id = JwtKit.parseId(accessToken);

                    stringRedisTemplate.opsForValue().set("sessionId","token~~~");
                    logger.info("redis的的额值"+stringRedisTemplate.opsForValue().get("sessionId"));

                    if (JwtKit.isWillExpire(accessToken)) {
                        logger.info("即将过期 refresh token");
                    }else{
                        logger.info("还未过期");
                    }
                    logger.info("userid==========>"+id);
                    logger.info("进入登陆状态");
                } catch (JwtException e) {
                    logger.info("捕获JWt异常");
                    response.getWriter().write(ResultBean.exceptionEnum2Json(ExceptionEnum.INVALID_TOKEN));
                    return;
                }
                chain.doFilter(request, response);
            }else{
                logger.info("token 不存在============");
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.getWriter().write(ResultBean.exceptionEnum2Json(ExceptionEnum.EMPTY_TOKEN));
                return;
            }
        } else {
            logger.info("过滤器白名单===================== url:" + optional.get());
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        excludedUrls = null;
    }
}
