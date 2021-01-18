package com.yuan.study.component;

import com.yuan.study.service.UserService;
import com.yuan.study.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 由于前后端分离没有session，只有token，每次有token的请求都会被拦截下来去数据库查出数据
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    // 定义token字符串在request头部的键名
    public static final String TOKEN_ON_HEADER = "token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        String token = request.getHeader(TOKEN_ON_HEADER);
        if (token != null) {
            String username = jwtUtil.getSubject(token);
            System.out.println("filter timing 1:" + (System.currentTimeMillis() - start));
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                // 这里大概会浪费5ms的时间从数据库获取数据,想到的优化：可以从登录返回token起，就把角色用户名放到token中
                // 然后解析token拿到这两个用来，要是要弃用token就难搞。所以可能还需要用到redis来帮一手
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        long end = System.currentTimeMillis();
        System.out.format("jwt filter total [%d]ms with %s\n",(end - start),request.getRequestURI());
        // 表示放行，执行下一个过滤器
        chain.doFilter(request, response);
    }
}
