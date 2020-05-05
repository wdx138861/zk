package com.example.springboot.filter;


import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

/**
 * @Author: wdx
 * @Data: 2020/4/27 23:32
 */
@WebFilter("/*")
public class SomeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        System.out.println("信息已经被过滤");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
