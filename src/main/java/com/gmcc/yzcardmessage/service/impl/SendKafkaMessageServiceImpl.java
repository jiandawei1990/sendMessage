package com.gmcc.yzcardmessage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gmcc.yzcardmessage.entity.AccountWallet;
import com.gmcc.yzcardmessage.service.SendKafkaMessageService;
import com.gmcc.yzcardmessage.service.TypicalTradeInfoService;
import com.gmcc.yzcardmessage.util.HttpUtil;
import com.gmcc.yzcardmessage.util.SHA256SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
public class SendKafkaMessageServiceImpl implements SendKafkaMessageService {

    private static Logger logger = LoggerFactory.getLogger(SendKafkaMessageServiceImpl.class);

    @Autowired
    private  CodeTradeInfoServiceImpl codeTradeInfoService;

    @Value("${send.bootstrap.servers}")
    private  String sendBootstrapServer;


    @Override
    public void sendKafkaTradeMessage() throws Exception {
        String username = "tjck";
        String password = "tjck@%&#*$pwd";
        String topic = "bd-etl-sync_tjck_posp"; // 目标topic
        String moduleClazz = "org.apache.kafka.common.security.scram.ScramLoginModule";
        String jaasConfig = String.format("%s required username= \"%s\" password= \"%s\";",
                         moduleClazz, username, password);

        Properties properties = new Properties();
        properties.put("acks", "all");
        properties.put("max.block.ms", 30_000);
        properties.put("linger.ms", "1");
        properties.put("bootstrap.servers", sendBootstrapServer);
        //183.230.153.41:60395,183.230.153.41:60396
        properties.put("key.serializer", StringSerializer.class);
        properties.put("value.serializer", StringSerializer.class);
        properties.put("sasl.mechanism", "SCRAM-SHA-256");
        properties.put("security.protocol", "SASL_PLAINTEXT");
        properties.put("sasl.jaas.config", jaasConfig);

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        boolean isRunning = true;
        List<String> idList = new ArrayList<>();

        while (isRunning) {  //发送数据

                // 每次1000
                List<AccountWallet> accountWalletList = codeTradeInfoService.getTradeInfo();
                logger.info("从本次记录待发送条数:" + accountWalletList.size());

                //如果没有数据的话，5秒后在进行数据处理
                if (accountWalletList.size() == 0) {
                    Thread.sleep(5000);
                }

                // 保持有数据的情况下发送数据
                if (accountWalletList.size() > 0) {
                    for (AccountWallet accountWallet : accountWalletList) {
                        Map<String, Object> sendMap = new HashMap<String, Object>();
                        //消费时间
                        sendMap.put("transactTime", accountWallet.getTransactTime());

                        //上送时间(当前时间)
                        sendMap.put("uploadTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));

                        //公司号
                        sendMap.put("branchCompany", accountWallet.getCompanyCode());

                        //线路号
                        sendMap.put("lineCode", accountWallet.getLineCode());
                        //车辆号
                        sendMap.put("busCode", accountWallet.getBusCode());

                        //设备号
                        sendMap.put("terminalCode", accountWallet.getDeviceCode());

                        String Transat_Type = accountWallet.getTransatType().toString();
                        //驾驶员卡号
                        sendMap.put("driverCardNo", accountWallet.getDeviceCardNo());    //驾驶员卡号

                        //直接获取卡号
                        sendMap.put("cardNo", accountWallet.getCardNo());
                        //consumeType：       脱机闪付 电子现金卡    unionCardPay      银联云闪付      unionPay   银联联机二维码    unionAppPay  银联脱机二维码 unionCodePay  支付宝二维码 alipayCode  云雷二维码 ： yunleiCodePay    实体卡：busCard

                        //上站点
                        sendMap.put("startStationName", accountWallet.getStartStationName());
                        //下站点
                        sendMap.put("endStationName", accountWallet.getEndStationName());//消费类型 qrcode 二维码 busCard 实体卡 unionpay 云闪付 支付宝
                        sendMap.put("consumeType", accountWallet.getTransatType());

                        sendMap.put("cardType", accountWallet.getCardType());          //卡类型
                        sendMap.put("cardTypeName", accountWallet.getCardTypeName());  //卡名称

                        // 发送数据
                        String jsonString = JSONObject.toJSONString(sendMap);
                        logger.info(jsonString);

                        // 同步机制
                        Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>(topic, jsonString));

                        RecordMetadata recordMetadata = future.get(30, TimeUnit.SECONDS);
                        logger.info("返回信息" + recordMetadata);

                        if (recordMetadata.topic() != "") {
                            logger.info(String.valueOf(recordMetadata.offset()));
                            idList.add(accountWallet.getId());
                        }
                    }
                }
                logger.info("进行本次上送标志更新");
                //批量更新发送的数据
                if (idList.size() > 0) {
                    codeTradeInfoService.updateTradeFlag(idList);
                    logger.info("本次完成上送的条数:" + idList.size());
                    //插入完成之后,清空List数据
                    idList.clear();
                }
            }
    }


}
