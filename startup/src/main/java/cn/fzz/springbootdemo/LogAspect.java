package cn.fzz.springbootdemo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by Andy on 2019/1/14.
 * Desc:
 */
@Component
@Aspect
public class LogAspect {
    @Pointcut("@annotation(cn.fzz.common.annotation.LogParameter)")
    private void log() { }

    /**
     * 定制一个环绕通知
     * @param joinPoint
     */
    @Around("log()")
    public void advice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Around Begin ");
        joinPoint.proceed();//执行到这里开始走进来的方法体（必须声明）
        System.out.println("Around End");
    }

    //当想获得注解里面的属性，可以直接注入改注解
    //方法可以带参数，可以同时设置多个方法用&&
    @Before("log()")
    public void record(JoinPoint joinPoint) {
        System.out.println("Before" + Arrays.toString(joinPoint.getArgs()));
    }

    @After("log()")
    public void after() {
        System.out.println("After");
    }
}
