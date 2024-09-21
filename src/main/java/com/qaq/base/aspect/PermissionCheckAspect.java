package com.qaq.base.aspect;

import com.qaq.base.annotation.CheckPermission;
import com.qaq.base.exception.UnAuthorizedException;
import com.qaq.base.model.Auth;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

@Aspect
public class PermissionCheckAspect {

    @Pointcut("@annotation(com.qaq.base.annotation.CheckPermission)")
    public void readCheckerPoint() {
    }

    @Before("readCheckerPoint()")
    public void advice(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        Auth auth = (Auth) request.getAttribute("auth");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        var checkPermission = method.getAnnotation(CheckPermission.class);

        String permissionNeed = checkPermission.value();
        check(auth, permissionNeed);
    }

    private void check(Auth auth, String permissionNeed) throws UnAuthorizedException {
        if (!auth.havePermission(permissionNeed)) {
            String msg = String.format("[%s][%s] no permission to operate, need permission %s",
                    auth.getToken().getEmail(), auth.getToken().getName(),
                    permissionNeed);
            throw new UnAuthorizedException(msg);
        }
    }

}
