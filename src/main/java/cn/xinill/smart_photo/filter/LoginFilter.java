package cn.xinill.smart_photo.filter;

import cn.xinill.smart_photo.service.impl.TokenServiceImpl;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Xinil
 * @Date: 2021/1/29 23:29
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        int id = -1;
        try {
            String token = ((HttpServletRequest)request).getHeader("token");
            if(token == null){
                System.err.println("token 为null");
//                request.getRequestDispatcher("/login.html").forward(request, response);
//                return;
            }else{
                id = new TokenServiceImpl().verifyUserToken(token);
                System.out.println("获取token id=" + id);
            }
            request.setAttribute("uid", id);
            filterChain.doFilter(request,response);
        } catch (Exception e) {//需要登陆
            e.printStackTrace();
            System.err.println("需要登陆");
            //request.getRequestDispatcher("/login.html").forward(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
