package com.libing.libingdemo.aspect;

import com.libing.libingdemo.annotation.UserAnno;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class UserAspect {
    @Around("@annotation(userAnno)")
    public Object around(ProceedingJoinPoint joinPoint, UserAnno userAnno ) throws Throwable {
        System.out.println("开始干活了！");
        //
        Object result = joinPoint.proceed();
        System.out.println(userAnno.value());
        System.out.println("活儿干完了！");
        return result;
    }
}
