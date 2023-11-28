//package com.gmcc.yzcardmessage.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.gmcc.yzcardmessage.entity.AccountWallet;
//import com.gmcc.yzcardmessage.service.TypicalTradeInfoService;
//import com.gmcc.yzcardmessage.util.HttpUtil;
//import com.gmcc.yzcardmessage.util.SHA256SignUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
////乐山考勤数据
//@Component
//@ConditionalOnProperty(value = "generator.DriverMessageSyn.start", havingValue = "enable")
//@Slf4j
//public class DriverWorkOnOffInfoTaskImpl {
//
//    @Value("${send.url}")
//    private  String sendUrl;
//
//    @Autowired
//    private TypicalTradeInfoService typicalTradeInfoService;
//
//    @Scheduled(cron = "${generator.DriverMessageSyn.quartzConfiguration.cronExpression}")
//    public void sendDirverInfoData() throws Exception{
//
//            List<String> idList = new ArrayList();
//            List<AccountWallet> accountWalletList = this.typicalTradeInfoService.getTradeInfo();
//            log.info("本次记录待发送条数:" + accountWalletList.size());
//
//            if (accountWalletList.size() > 0)
//            {
//                for (AccountWallet accountWallet : accountWalletList)
//                {
//                    Map<String, Object> sendMap = new HashMap();
//                    sendMap.put("transactTime", accountWallet.getTransactTime());
//                    sendMap.put("uploadTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
//                    sendMap.put("branchCompany", accountWallet.getCompanyCode());
//                    sendMap.put("lineCode", accountWallet.getLineCode());
//                    sendMap.put("busCode", accountWallet.getBusCode());
//                    sendMap.put("driverCardNo", accountWallet.getDeviceCardNo());
//                    sendMap.put("cardNo", accountWallet.getCardNo());
//                    sendMap.put("consumeType", accountWallet.getTransatType());
//                    byte[] sign256 = SHA256SignUtil.sign256(JSON.toJSONString(sendMap));
//                    String sign = SHA256SignUtil.encodeBase64(sign256);
//                    sendMap.put("signType", "SHA256");
//                    sendMap.put("sign", sign);
//
//                    String result = "";
//                    try
//                    {
//                        result = HttpUtil.sendPostForGongAn(this.sendUrl, sendMap);
//                        log.info("发送数据:\n" + sendMap);
//                        log.info("返回的结果为：" + result);
//                    }
//                    catch (IOException e)
//                    {
//                        e.printStackTrace();
//                    }
//                    if (result != "")
//                    {
//                        Map<String, Object> resultMap = JSON.parseObject(result);
//                        if (resultMap.get("result").toString().equals("0000")) {
//                            idList.add(accountWallet.getId());
//                        }
//                    }
//                }
//                if (idList.size() > 0)
//                {
//                    this.typicalTradeInfoService.updateTradeFlag(idList);
//                    log.info("本次完成上送的条数:" + idList.size());
//
//                    idList.clear();
//                }
//            }
//
//    }
//
//
//
//}
