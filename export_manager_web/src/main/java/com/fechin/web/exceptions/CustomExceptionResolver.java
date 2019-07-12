package com.fechin.web.exceptions;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if(ex instanceof UnauthorizedException){
            return new ModelAndView("forward:/unauthorized.jsp");
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(ex);
        modelAndView.setViewName("error");
        modelAndView.addObject("errorMsg", ex.getMessage());
        return modelAndView;
    }
}
