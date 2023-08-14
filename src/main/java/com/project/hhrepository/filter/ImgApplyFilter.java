/**
 * @author Valar Morghulis
 * @Date 2023/8/2
 */
package com.project.hhrepository.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;


public class ImgApplyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String servletPath = req.getServletPath();
        if (servletPath.contains("/img/upload")){
            filterChain.doFilter(req, resp);
        }
    }
}
