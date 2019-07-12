package com.fechin.web.log;

import com.fechin.web.controller.BaseController;
import com.fechin.domain.system.log.Log;
import com.fechin.domain.system.user.User;
import com.fechin.service.system.log.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

@Component
@Aspect
public class SysLog extends BaseController {
    @Autowired
    private LogService logService;

    @Pointcut("execution(* com.fechin.web.controller.*..*.*(..))")
    private void pt1() {
    }


    //AOP
    @Around("pt1()")
    public Object saveLog(ProceedingJoinPoint proceedingJoinPoint) {
        System.out.println("进入切面");
        Object result = null;
        User userInfo = (User) session.getAttribute("userInfo");
        try {
            Object[] args = proceedingJoinPoint.getArgs();
            //获取签名
            MethodSignature signature = (MethodSignature)proceedingJoinPoint.getSignature();
            Method method = signature.getMethod();
            if (method.isAnnotationPresent(RequestMapping.class)){
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                /*String path = requestMapping.path()[0];*/
                String name = requestMapping.name();
                Log log = new Log();
                log.setAction(requestMapping.name());
                log.setCompanyId(userInfo.getCompanyId());
                log.setId(UUID.randomUUID().toString().replace("-",""));
                log.setUserName(userInfo.getUserName());
                log.setTime(new Date());
                log.setCompanyName(userInfo.getCompanyName());
                log.setMethod(method.getName());
                log.setIp(request.getRemoteAddr());
                logService.save(log);
            }
            result = proceedingJoinPoint.proceed(args);
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return result;
        }
    }
}
