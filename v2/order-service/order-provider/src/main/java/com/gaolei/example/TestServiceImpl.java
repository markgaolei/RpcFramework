package com.gaolei.example;

@PerRemoteService
public class TestServiceImpl implements ITestService {
    @Override
    public String say() {
        return "Hello Mark";
    }
}
