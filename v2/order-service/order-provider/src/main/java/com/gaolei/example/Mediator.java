package com.gaolei.example;

import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Mediator {
    public static Map<String, BeanMethod> map = new ConcurrentHashMap<>();

    private static volatile Mediator mediator;

    private Mediator(){}

    public static Mediator getInstance(){
        if (mediator == null){
            synchronized (Mediator.class){
                if (mediator == null){
                    mediator = new Mediator();
                }
            }
        }
        return mediator;
    }

    public Object processor(RpcRequest request) throws InvocationTargetException, IllegalAccessException {
        String key = request.getClassName() + "." + request.getMethodName();
        BeanMethod beanMethod = Mediator.map.get(key);
        if (beanMethod == null) return null;
        Object bean = beanMethod.getBean();
        Method method = beanMethod.getMethod();
        return  method.invoke(bean,request.getArgs());
    }

}
