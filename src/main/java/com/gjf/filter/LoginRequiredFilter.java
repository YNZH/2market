package com.gjf.filter;

import com.gjf.config.UrlProperties;
import com.gjf.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * 过滤用户(未登录)的url请求 可自行配置无需过滤的url白名单
 * @Author: GJF
 * @Date : 2018/04/23
 * Time   : 16:21
 */
@WebFilter(urlPatterns = "/api/*")
public class LoginRequiredFilter implements Filter {

    private String excludedUrls;
    private HttpServletRequest rq;
    private Optional<String> optional;
    @Autowired
    private UrlProperties urlProperties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludedUrls = urlProperties.getExcluded();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        rq = (HttpServletRequest)request;
        if (excludedUrls!=null && excludedUrls.length()>0) {
            optional = Arrays.stream(excludedUrls.split(urlProperties.getSeparator()))
                    .filter(x -> x.equalsIgnoreCase(rq.getServletPath()))
                    .findFirst();
        }
        if (!optional.isPresent()) {
            System.out.println("=========================url:" + rq.getServletPath() + "进入过滤器需要验证token===================== url:");
            String accessToken = request.getParameter("token");
            if (accessToken != null && accessToken.length() > 0) {
                JWTUtil.parseId(accessToken);
            }
            System.out.println("token 不存在============");
        }else{
            System.out.println("过滤器白名单===================== url:" + optional.get());
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        excludedUrls = null;
    }


}
