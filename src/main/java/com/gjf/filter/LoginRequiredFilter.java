package com.gjf.filter;

import com.gjf.utils.JWTUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

/**
 * @Author: GJF
 * @Date : 2018/04/23
 * Time   : 16:21
 */
@WebFilter(urlPatterns = "/api/*",
        initParams = {@WebInitParam(name = "EXCLUDED_PAGES", value = "/api/login.do,/api/register.do")})
public class LoginRequiredFilter implements Filter {

    private String excludedPages;
    private String[] excludedPageArray;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludedPages = filterConfig.getInitParameter("EXCLUDED_PAGES");
        if (excludedPages != null && excludedPages.length() > 0) {
            excludedPageArray = excludedPages.split(String.valueOf(','));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest)request;
        if (excludedPageArray!=null && excludedPageArray.length>0) {
            for (String pageUrl:excludedPageArray
                 ) {
                if(pageUrl.equalsIgnoreCase(rq.getServletPath())){
                    chain.doFilter(request,response);
                }
            }
        }

        //拦截之后 验证登陆
        else{
            String accessToken = request.getParameter("token");
            if (accessToken !=null && accessToken.length()>0){
                JWTUtil.parseId(accessToken);
            }
            chain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {
        excludedPageArray = null;
        excludedPages = null;

    }


}
