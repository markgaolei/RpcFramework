package com.gaolei.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RpcInvocationHandler implements InvocationHandler {
    private String host;
    private int port;

    public RpcInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //通过socket创建连接
        RpcNetTransport rpcNetTransport = new RpcNetTransport(host,port);
        rpcNetTransport.createSocket();
        //传输请求的数据 请求接口名称，方法名称，参数等
        RpcRequest request = new RpcRequest();
        request.setArgs(args);
        request.setMethodName(method.getName());
        request.setTypes(method.getParameterTypes());
        request.setClassName(method.getDeclaringClass().getName());
        return rpcNetTransport.send(request);
    }
}
