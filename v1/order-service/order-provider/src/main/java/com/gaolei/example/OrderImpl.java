package com.gaolei.example;

public class OrderImpl implements IOrderService{
    @Override
    public String getOrderList() {
        return "EXECUTE GET_ORDER_LIST METHOD";
    }

    @Override
    public String getById(String id) {
        return "EXECUTE GET_BY_ID METHOD";
    }
}
