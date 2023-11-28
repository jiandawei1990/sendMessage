package com.gmcc.yzcardmessage.service;

public interface SendTradeMessage {
    //发送城市卡服务器数据
    public void sendGongAnTradeMessage()throws Exception;

    public void sendAlipayTradeMessage()throws Exception;

    public void sendQrtForAlipayTradeMessage()throws Exception;
}
