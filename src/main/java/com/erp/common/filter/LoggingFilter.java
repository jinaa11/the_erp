package com.erp.common.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;


// URI 추적 로깅 


@WebFilter("/*")
public class LoggingFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();
        
        if (!uri.matches(".*(\\.(jsp|png|jpg|css|js|woff2|ico|svg|map|env))$"))
            System.out.println("요청 URI: " + uri);
        

        chain.doFilter(request, response);
    }
}
