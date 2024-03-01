package com.xw.cloud.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ZQQ
 * @version 1.0
 * @date 2021/9/22 15:54
 * @desc :
 */
@WebFilter(urlPatterns = "/*", filterName = "responseHeadFilter")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, IOException, IOException {
        //增加响应头缺失代码
        HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse res=(HttpServletResponse)response;
        res.addHeader("X-Frame-Options","SAMEORIGIN");
        res.addHeader("Referrer-Policy","origin");
        res.addHeader("Content-Security-Policy","object-src 'self'");
        res.addHeader("X-Permitted-Cross-Domain-Policies","master-only");
        res.addHeader("X-Content-Type-Options","nosniff");
        res.addHeader("X-XSS-Protection","1; mode=block");
        res.addHeader("X-Download-Options","noopen");
        res.addHeader("Strict-Transport-Security","max-age=63072000; includeSubdomains; preload");
        //处理cookie问题
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String value = cookie.getValue();
                StringBuilder builder = new StringBuilder();
                builder.append(cookie.getName()+"="+value+";");
                builder.append("Secure;");//Cookie设置Secure标识
                builder.append("HttpOnly;");//Cookie设置HttpOnly
                res.addHeader("Set-Cookie", builder.toString());
            }
        }
        chain.doFilter(request, response);
    }
    @Override
    public void destroy() {

    }
}

