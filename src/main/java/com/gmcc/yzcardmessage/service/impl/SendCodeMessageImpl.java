package com.gmcc.yzcardmessage.service.impl;

import com.alibaba.fastjson.JSON;
import com.gmcc.yzcardmessage.entity.AccountWallet;
import com.gmcc.yzcardmessage.service.SendCodeMessage;
import com.gmcc.yzcardmessage.service.TypicalTradeInfoService;
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

        @Value("${send.url}")
        private  String sendUrl;



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

                //如果没有数据的话，10秒后在进行数据处理
                if(accountWalletList.size()==0){
                    Thread.sleep(10000);
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
                        if(Transat_Type.equals("unionpay")){
                            sendMap.put("driverCardNo", driverHeadCode + accountWallet.getDeviceCardNo());    //银联ODA司机卡号8位
                        }else {
                            sendMap.put("driverCardNo", driverHeadCode + SHA256SignUtil.covert(accountWallet.getDeviceCardNo()));
                        }

                        //乘车人卡号(01是正常卡  07是交通部卡)
                        if (accountWallet.getCardCategory().equals("01")) {
                            sendMap.put("cardNo", cityCode + SHA256SignUtil.covert(accountWallet.getCardNo()));
                        } else {
                            if(Transat_Type.equals("unionpay")){//银联卡去掉后面的
                                sendMap.put("cardNo",accountWallet.getCardNo().replaceAll("F+$", ""));
                            }else {
                                sendMap.put("cardNo", accountWallet.getCardNo());
                            }
                        }

                        //消费类型 qrcode 二维码 busCard 实体卡 unionpay 云闪付
                        if(Transat_Type.equals("wxCode")){
                            sendMap.put("consumeType","wxCode");
                        }else if(Transat_Type.equals("alipayCode")){
                            sendMap.put("consumeType","alipayCode");
                        }else if(Transat_Type.equals("zyCode")){
                            sendMap.put("consumeType","zyCode");
                        }else if(Transat_Type.equals("unionPayCode")){
                            sendMap.put("consumeType","unionPayCode");
                        }else if(Transat_Type.equals("unionpay")){
                            sendMap.put("consumeType","unionpay");
                        }else {
                            sendMap.put("consumeType", "busCard");
                        }

                        // 生成签名
                        byte[] sign256 = SHA256SignUtil.sign256(JSON.toJSONString(sendMap));
                        String sign = SHA256SignUtil.encodeBase64(sign256);

                        sendMap.put("signType", "SHA256");
                        sendMap.put("sign",sign);

                        String result = "";
                        try {
                            result = HttpUtil.sendPostForGongAn(sendUrl, sendMap);
                            logger.info("发送数据:\n" + sendMap);
                            logger.info("返回的结果为：" + result);
                        } catch (IOException e) {
                         //
                            idList.add(accountWallet.getId());
                         //
                            e.printStackTrace();
                        }

                        if(result!="") {
                            Map<String, Object> resultMap = JSON.parseObject(result);

                            //判断是是否接受成功

                            if (resultMap.get("code").toString().equals("0")) {
                                idList.add(accountWallet.getId());
                            }
                        }
                    }

                    logger.info("update");
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

