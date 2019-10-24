package com.exoa.oasystem.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zlian
 * @date 2019/7/17  14:51
 * @Description: TODO
 */

//@Component
//@Aspect
public class WebAspect {
    private Map<Long, Map<String, List<Long>>> threadMap = new ConcurrentHashMap<>(200);

    //匹配com.exoa.controller包及其子包下的所有类的所有方法
    @Pointcut("execution(* com.exoa.oasystem.controller.*.*(..))")
    public void executeService() {

    }

    @Before("executeService()")
    public void doBeforeAdvice(JoinPoint joinPoint) {
        System.out.println(joinPoint.toShortString() + "开始");
        Map<String, List<Long>> methodTimeMap = threadMap.get(Thread.currentThread().getId());
        List<Long> list;
        if (methodTimeMap == null) {
            methodTimeMap = new HashMap<>();
            list = new LinkedList<>();
            list.add(System.currentTimeMillis());
            methodTimeMap.put(joinPoint.toShortString(), list);
            threadMap.put(Thread.currentThread().getId(), methodTimeMap);
        } else {
            list = methodTimeMap.get(joinPoint.toShortString());
            if (list == null) list = new LinkedList<>();
            list.add(System.currentTimeMillis());
            methodTimeMap.put(joinPoint.toShortString(), list);
        }
        System.out.println(threadMap + "---" + methodTimeMap + "---" + list);
    }

    @After("executeService()")
    public void doAfterAdvice(JoinPoint joinPoint) {
        Object[] joinPointArgs = joinPoint.getArgs();
        joinPoint.getThis();
        joinPoint.getTarget();
        Signature joinPointSignature = joinPoint.getSignature();
        System.out.println("代理方法:" + joinPointSignature.getName());
        System.out.println("AOP代理类的名字:" + joinPointSignature.getDeclaringTypeName());
        joinPointSignature.getDeclaringType();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        Enumeration<String> requestParameterNames = request.getParameterNames();
        HashMap<String, String> paramHashMap = new HashMap<>();
        while (requestParameterNames.hasMoreElements()) {
            String paramelement = requestParameterNames.nextElement();
            paramHashMap.put(paramelement, request.getParameter(paramelement));
        }
        String jsonString = JSON.toJSONString(paramHashMap);
        if (joinPointArgs.length > 0) {
            System.out.println("请求参数:" + jsonString);
        }
        System.out.println(joinPoint.toShortString() + "结束");
        Map<String, List<Long>> methodTimeMap = threadMap.get(Thread.currentThread().getId());
        List<Long> list = methodTimeMap.get(joinPoint.toShortString());
        System.out.println("代理方法:" + joinPointSignature.getName() + ",耗时:" + (System.currentTimeMillis() - list.get(list.size() - 1)));
        list.remove(list.size() - 1);
        System.out.println(threadMap + "---" + methodTimeMap + "---" + list);
    }
}
