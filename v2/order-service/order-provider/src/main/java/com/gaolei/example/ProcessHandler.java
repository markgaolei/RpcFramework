package com.gaolei.example;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class ProcessHandler implements Runnable {

    private Socket socket;

    public ProcessHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        InputStream inputStream = null;

        try {
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            RpcRequest request = (RpcRequest)objectInputStream.readObject();
            //单例模式获取Mediator
            Mediator mediator = Mediator.getInstance();
            Object rs = mediator.processor(request);
            System.out.println("服务端处理执行的结果" + rs);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(rs);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            //TODO 关闭流
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objectInputStream != null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objectOutputStream != null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Deprecated
    private Object invoke(RpcRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (request.getClassName() == null || Mediator.map == null){
            return null;
        }
        String key = request.getClass().getInterfaces()[0] + request.getMethodName();
        if (Mediator.map.get(key) != null){
            Class clazz = Class.forName(request.getClassName());
            Method method = clazz.getMethod(request.getMethodName(),request.getTypes());
            return method.invoke(Mediator.map.get(key).getBean(), request.getArgs());
        }
        return null;
    }
}
