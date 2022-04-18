package com.gmcc.yzcardmessage.service;

public interface SendKafkaMessageService {

    //发送城市卡消息队列数据
    public void sendKafkaTradeMessage()throws Exception;

}
