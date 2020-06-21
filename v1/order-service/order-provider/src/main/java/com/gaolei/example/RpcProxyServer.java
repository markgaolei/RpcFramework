package com.gaolei.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcProxyServer {
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void publisher(Object service, int port){
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(port);
            while (true){
                socket = serverSocket.accept();
                executorService.submit(new ProcessHandler(socket, service));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
