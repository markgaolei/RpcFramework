package com.gaolei.example;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        IOrderService orderService = null;
        ITestService testService = null;
        RpcProxyClient rpcProxyClient = new RpcProxyClient();
        orderService = rpcProxyClient.clientProxy(IOrderService.class,"localhost",8080);
        testService = rpcProxyClient.clientProxy(ITestService.class,"localhost",8080);
        System.out.println(orderService.getOrderList());
        System.out.println(testService.say());
    }
}
