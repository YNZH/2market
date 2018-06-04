package com.gjf.filter;

import com.gjf.config.UrlProperties;
import com.gjf.exception.ExceptionEnum;
import com.gjf.model.ResultBean;
import com.gjf.utils.JwtKit;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;

import static com.gjf.utils.JwtKit.tokenProperties;

/**
 * 过滤用户(未登录)的url请求 可自行配置无需过滤的url白名单
 *
 * @Author: GJF
 * @Date : 2018/04/23
 * Time   : 16:21
 */
@WebFilter(urlPatterns = "/api/*", asyncSupported = true)
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
            logger.info("token======>" + accessToken);
            if (accessToken != null && accessToken.length() > 0) {
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                Long id = 0L;
                try {
                    Claims claims = JwtKit.parseClaimsBody(accessToken);
                    id = Long.valueOf(claims.getId());
                    if (JwtKit.isWillExpire(accessToken)) {
                        logger.info("refresh token 验证token是否为废弃的");
                        String refreshToken = stringRedisTemplate.opsForValue().get("JWT-SESSION-" + id);
                        if (null != refreshToken && refreshToken.equalsIgnoreCase(accessToken)) {
                            logger.info("token进入在刷新期间第一次进入 reissue颁发！！！");
                            reissued((HttpServletResponse) response, id, claims.getSubject());
                        }else {
                            logger.info("token为废弃的的与更新的不一致重新登陆吧孩子");
                            response.getWriter().write(ResultBean.exceptionEnum2Json(ExceptionEnum.OUTDATED_TOKEN));
                        }
                    } else {
                        logger.info("还未进入refreshTime");
                    }
                }catch (ExpiredJwtException expired){
                    //过期~
                    logger.info("token过期了");
                    String sessionId = stringRedisTemplate.opsForValue().get(id.toString());
                    stringRedisTemplate.delete(id.toString());
                    stringRedisTemplate.delete(sessionId);
                    response.getWriter().write(ResultBean.exceptionEnum2Json(ExceptionEnum.INVALID_TOKEN));
                    return;
                }catch (JwtException e) {
                    logger.info("捕获JWt异常");
                    response.getWriter().write(ResultBean.exceptionEnum2Json(ExceptionEnum.INVALID_TOKEN));
                    return;
                }
                chain.doFilter(request, response);
            } else {
                logger.info("token 不存在============");
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.getWriter().write(ResultBean.exceptionEnum2Json(ExceptionEnum.EMPTY_TOKEN));
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

    private void reissued(HttpServletResponse response, Long id, String nickname) {
        String newToken = JwtKit.generateToken(id.toString(), nickname, tokenProperties.getExpireTime());
        stringRedisTemplate.opsForValue().set("JWT-SESSION-"+id,newToken,tokenProperties.getExpireTime(), TimeUnit.MILLISECONDS);
        Cookie cookie = new Cookie("access_token", newToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/");
        cookie.setMaxAge((int) (tokenProperties.getExpireTime() / 1000));
        response.addCookie(cookie);
    }
}
