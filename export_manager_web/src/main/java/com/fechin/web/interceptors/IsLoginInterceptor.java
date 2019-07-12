package com.fechin.web.interceptors;

import com.fechin.domain.system.user.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IsLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User userInfo = (User) session.getAttribute("userInfo");
        if(userInfo!=null){
            return true;
        }

        response.sendRedirect(request.getContextPath()+"/login.jsp");
        return false;
    }
}
