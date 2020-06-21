package com.gaolei.example;

import java.lang.reflect.Proxy;

public class RpcProxyClient {
    public <T>T clientProxy(final Class<T> interfaceCls, String host, int port){
        return (T)Proxy.newProxyInstance(interfaceCls.getClassLoader(), new Class<?>[]{interfaceCls}, new RpcInvocationHandler(host, port));
    }
}
