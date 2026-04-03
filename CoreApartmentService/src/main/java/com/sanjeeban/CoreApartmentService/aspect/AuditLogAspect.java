package com.sanjeeban.CoreApartmentService.aspect;

import com.sanjeeban.CoreApartmentService.annotations.AuditLog;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditLogAspect {

    @Before("@annotation(auditLog)")
    public void logMethod(JoinPoint joinpoint, AuditLog auditLog){

        String methodName = joinpoint.getSignature().getName();
        String action = auditLog.action();

        System.out.println("AUDIT LOG -> "+action+" Method: "+methodName);
    }

}
