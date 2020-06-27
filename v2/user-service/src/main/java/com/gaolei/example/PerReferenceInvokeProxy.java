package com.gaolei.example;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class PerReferenceInvokeProxy implements BeanPostProcessor {

    @Autowired
    RpcInvocationHandler rpcInvocationHandler;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field: fields){
            if (field.isAnnotationPresent(PerReference.class)){
                field.setAccessible(true);
                Object proxy = Proxy.newProxyInstance(field.getType().getClassLoader(),new Class<?>[]{field.getType()},rpcInvocationHandler);
                try {
                    field.set(bean,proxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }
}
