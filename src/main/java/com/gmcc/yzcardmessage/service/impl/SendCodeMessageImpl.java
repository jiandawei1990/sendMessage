package com.gmcc.yzcardmessage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gmcc.yzcardmessage.entity.AccountWallet;
import com.gmcc.yzcardmessage.service.SendCodeMessage;
import com.gmcc.yzcardmessage.util.HttpUtil;
import com.gmcc.yzcardmessage.util.SHA256SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
@Service
public class SendCodeMessageImpl implements SendCodeMessage {

        private static Logger logger = LoggerFactory.getLogger(SendCodeMessageImpl.class);

        @Value("${send.url1}")
        private  String sendUrl1;

        @Autowired
        private  CodeTradeInfoServiceImpl codeTradeInfoService;

        public static final String KEY_ALGORITHM = "RSA";

        private static final String cityCode = "22500000";

        private static final String driverHeadCode="00022500000";

        @Override
        public void sendGongAnCodeTradeMessage() throws Exception{

            logger.info("从数据库进行传输");

            boolean isRunning = true;
            List<String> idList = new ArrayList<>();

            while (isRunning) {  //发送数据
                // 每次1000
                List<AccountWallet> accountWalletList = codeTradeInfoService.getTradeInfo();
                logger.info("从本次记录待发送条数:"+accountWalletList.size());

                //如果没有数据的话，5秒后在进行数据处理
                if(accountWalletList.size()==0){
                    Thread.sleep(5000);
                }

                // 保持有数据的情况下发送数据
                if (accountWalletList.size() > 0) {

                    for (AccountWallet accountWallet : accountWalletList) {
                        Map<String, Object> sendMap = new HashMap<String,Object>();
                        //消费时间
                        sendMap.put("transactTime", accountWallet.getTransactTime());

                        //上送时间(当前时间)
                        sendMap.put("uploadTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

                        //公司号
                        sendMap.put("branchCompany", accountWallet.getCompanyCode());

                        //线路号
                        sendMap.put("lineCode", accountWallet.getLineCode());
                        //车辆号
                        sendMap.put("busCode", accountWallet.getBusCode());
                        String Transat_Type = accountWallet.getTransatType().toString();
                        //驾驶员卡号
                        sendMap.put("driverCardNo",  accountWallet.getDeviceCardNo());    //驾驶员卡号

                        //直接获取卡号
                        sendMap.put("cardNo",accountWallet.getCardNo());
                        //consumeType：       脱机闪付 电子现金卡    unionCardPay      银联云闪付      unionPay   银联联机二维码    unionAppPay  银联脱机二维码 unionCodePay  支付宝二维码 alipayCode  云雷二维码 ： yunleiCodePay    实体卡：busCard

                        //上站点
                        sendMap.put("startStationName",accountWallet.getStartStationName());
                        //下站点
                        sendMap.put("endStationName",accountWallet.getEndStationName());//消费类型 qrcode 二维码 busCard 实体卡 unionpay 云闪付 支付宝
                        sendMap.put("consumeType",accountWallet.getTransatType());

                        // 生成签名
                        byte[] sign256 = SHA256SignUtil.sign256(JSON.toJSONString(sendMap));
                        String sign = SHA256SignUtil.encodeBase64(sign256);

                        sendMap.put("signType", "SHA256");
                        sendMap.put("sign",sign);

                        String result = "";
                        try {
                            result = HttpUtil.sendPostForGongAn(sendUrl1, sendMap);
                            logger.info("发送数据:\n" + sendMap);
                            logger.info("返回的结果为：" + result);
                        } catch (IOException e) {

                            e.printStackTrace();
                        }

                        if(result!="") {
                            Map<String, Object> resultMap = JSON.parseObject(result);

                            //判断是是否接受成功
                            JSONObject jsonObject = JSONObject.parseObject(resultMap.get("msgheader").toString());
                            if (jsonObject.get("result").toString().equals("0000")) {
                                idList.add(accountWallet.getId());
                            }
                        }
                    }
                    logger.info("进行本次上送标志更新");
                    //批量更新发送的数据
                    if(idList.size()>0) {
                        codeTradeInfoService.updateTradeFlag(idList);
                        logger.info("本次完成上送的条数:"+idList.size());
                        //插入完成之后,清空List数据
                        idList.clear();
                    }
                }
            }
        }
    }

