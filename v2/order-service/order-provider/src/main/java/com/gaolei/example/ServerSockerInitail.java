package com.gaolei.example;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Spring容器启动完成后，会发布ContextRefreshedEvent
@Component
public class ServerSockerInitail implements ApplicationListener<ContextRefreshedEvent> {
    //线程池用来管理连接，防止阻塞
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //使用ServerSocket创建监听
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8888);
            while (true){
                Socket socket = serverSocket.accept();
                executorService.submit(new ProcessHandler(socket));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
