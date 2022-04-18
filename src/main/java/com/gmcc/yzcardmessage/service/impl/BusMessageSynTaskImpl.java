package com.gmcc.yzcardmessage.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gmcc.yzcardmessage.entity.BusLineinfo;
import com.gmcc.yzcardmessage.entity.Businfo;
import com.gmcc.yzcardmessage.entity.Cardinfo;
import com.gmcc.yzcardmessage.entity.PosBusinfo;
import com.gmcc.yzcardmessage.service.MessageSynService;
import com.gmcc.yzcardmessage.util.HttpUtil;
import com.gmcc.yzcardmessage.util.SHA256SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 定时发送车辆信息的数据和IC卡数据
 */
@Component
@ConditionalOnProperty(value = "generator.BusMessageSyn.start", havingValue = "enable")
public class BusMessageSynTaskImpl{
    @Autowired
    MessageSynService messageSynService;
    private static final int threadCount = 2;

    private static  int sum = 200;

    @Value("${send.url1}")
    private  String sendUrl;

    private static Logger logger = LoggerFactory.getLogger(BusMessageSynTaskImpl.class);

    @Scheduled(cron = "${generator.BusMessageSyn.quartzConfiguration.cronExpression}")
    public void BusInfoSendData() throws Exception{

            //车辆机具映射关系
            sendVechileDeviceMappingData();

            //车载机线路映射信息
            sendDeviceRouteMappingData();

            //车辆信息
            sendVechileInfoData();

//        //开启两个线程,根据奇数或偶数发送IC卡数据
//        for(int i=0;i<threadCount;i++) {
//
//            int num = i;
//            Thread sendThread = new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    try {
//                        while(true){
//                            List<Cardinfo> list = getIcCardData(num);
//                            //如果数据为空跳出循环
//                            if(list.size() ==0){
//                                break;
//                            }
//                            //如果有数据,调用发送接口
//                            setSendIcCardData(list);
//                             Thread.sleep(900);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            sendThread.setName("发送IC卡数据");
//            sendThread.start();
//        }

    }


    /**
     * 车辆机具映射关系
     * @return
     * @throws Exception
     */
    private int sendVechileDeviceMappingData() throws  Exception{

        List<PosBusinfo> list =  messageSynService.getVechileDeviceMappingData();

        List<String> idList = new ArrayList<>();

        for(PosBusinfo posBusinfo : list){
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("posmachineCode", posBusinfo.getPosmachineCode());
            sendMap.put("busCode", posBusinfo.getBusCode());
            sendMap.put("busName", posBusinfo.getBusName());

            // 生成签名
            byte[] sign256 = SHA256SignUtil.sign256(JSON.toJSONString(sendMap));
            String sign = SHA256SignUtil.encodeBase64(sign256);
            sendMap.put("signType", "SHA256");
            sendMap.put("sign", sign);
            String result = "";
            try {
                result = HttpUtil.sendPostForGongAn(sendUrl, sendMap);
                logger.info("发送数据:\n" + sendMap);
                logger.info("返回的结果为：" + result);
            } catch (IOException e) {

                e.printStackTrace();
            }

            if (result != "") {
                Map<String, Object> resultMap = JSON.parseObject(result);

                //判断是是否接受成功
                JSONObject jsonObject = JSONObject.parseObject(resultMap.get("msgheader").toString());
                if (jsonObject.get("result").toString().equals("0000")) {
                    idList.add(posBusinfo.getPosmachineCode());
                }
            }
        }
        if(idList.size()>0){
            //   messageSynService.updateCardInfoFlag(idList);
            logger.info("本次完成上送的条数:" + idList.size());
            //插入完成之后,清空List数据
            idList.clear();
        }
            return 0;
    }

    /**
     *车载机线路映射信息
     * @return
     * @throws Exception
     */
    private int sendDeviceRouteMappingData() throws  Exception{
        List<BusLineinfo> list =  messageSynService.getDeviceRouteMappingData();

        List<String> idList = new ArrayList<>();

        for(BusLineinfo busLineinfo : list){
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("lineName", busLineinfo.getLineName());
            sendMap.put("lineCode", busLineinfo.getLineCode());
            sendMap.put("posmachineCode", busLineinfo.getPosmachineCode());

            // 生成签名
            byte[] sign256 = SHA256SignUtil.sign256(JSON.toJSONString(sendMap));
            String sign = SHA256SignUtil.encodeBase64(sign256);
            sendMap.put("signType", "SHA256");
            sendMap.put("sign", sign);

            String result = "";
            try {
                result = HttpUtil.sendPostForGongAn(sendUrl, sendMap);
                logger.info("发送数据:\n" + sendMap);
                logger.info("返回的结果为：" + result);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (result != "") {
                Map<String, Object> resultMap = JSON.parseObject(result);

                //判断是是否接受成功
                JSONObject jsonObject = JSONObject.parseObject(resultMap.get("msgheader").toString());
                if (jsonObject.get("result").toString().equals("0000")) {
                    idList.add(busLineinfo.getPosmachineCode());
                }
            }
        }

        //更新发送成功的的设备信息
        if (idList.size() > 0) {
            //   messageSynService.updateCardInfoFlag(idList);
            logger.info("本次完成上送的条数:" + idList.size());
            //插入完成之后,清空List数据
            idList.clear();
        }
        return  0;
    }


    /**
     * 发送车辆信息数据
     * @return
     * @throws Exception
     */
    private int sendVechileInfoData() throws Exception{

        List<Businfo>   BusinfoList = messageSynService.getVechileInfoData();

        List<String> idList = new ArrayList<>();

        for (Businfo businfo : BusinfoList) {
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("busName", businfo.getBusName());
            sendMap.put("busCode", businfo.getBusCode());

            // 生成签名
            byte[] sign256 = SHA256SignUtil.sign256(JSON.toJSONString(sendMap));
            String sign = SHA256SignUtil.encodeBase64(sign256);
            sendMap.put("signType", "SHA256");
            sendMap.put("sign", sign);

            String result = "";
            try {
                result = HttpUtil.sendPostForGongAn(sendUrl, sendMap);
                logger.info("发送数据:\n" + sendMap);
                logger.info("返回的结果为：" + result);
            } catch (IOException e) {

                e.printStackTrace();
            }

            if (result != "") {
                Map<String, Object> resultMap = JSON.parseObject(result);

                //判断是是否接受成功
                JSONObject jsonObject = JSONObject.parseObject(resultMap.get("msgheader").toString());
                if (jsonObject.get("result").toString().equals("0000")) {
                    idList.add(businfo.getBusCode());
                }
            }
        }
            //更新所有发送成功的记录
            if (idList.size() > 0) {
             //   messageSynService.updateCardInfoFlag(idList);
                logger.info("本次完成上送的条数:" + idList.size());
                //插入完成之后,清空List数据
                idList.clear();
            }
        return 0;
    }


    /**
     * 查询IC 卡数据
     * @param num
     * @return
     */
    private List<Cardinfo> getIcCardData(int num){
       List<Cardinfo>  list=  messageSynService.getIcCardData(num);
        return list;
    }


    /**
     * 发送IC卡数据
     * @param list
     * @throws Exception
     */
    private void setSendIcCardData(List<Cardinfo> list) throws Exception {
        List<String> idList = new ArrayList<>();
        for (Cardinfo cardInfo : list) {
            Map<String, Object> sendMap = new HashMap<String, Object>();
            sendMap.put("cardNo", cardInfo.getCardNo());
            sendMap.put("cardCSN", cardInfo.getCardCSN());
            sendMap.put("userBirthday", cardInfo.getUserBirthday());
            sendMap.put("userSex", cardInfo.getUserSex());
            sendMap.put("cardType", cardInfo.getCardType());

            // 生成签名
            byte[] sign256 = SHA256SignUtil.sign256(JSON.toJSONString(sendMap));
            String sign = SHA256SignUtil.encodeBase64(sign256);
            sendMap.put("signType", "SHA256");
            sendMap.put("sign", sign);

            String result = "";
            try {
                result = HttpUtil.sendPostForGongAn(sendUrl, sendMap);
                logger.info("发送数据:\n" + sendMap);
                logger.info("返回的结果为：" + result);
            } catch (IOException e) {

                e.printStackTrace();
            }

            if (result != "") {
                Map<String, Object> resultMap = JSON.parseObject(result);

                //判断是是否接受成功
                JSONObject jsonObject = JSONObject.parseObject(resultMap.get("msgheader").toString());
                if (jsonObject.get("result").toString().equals("0000")) {
                    idList.add(cardInfo.getCardNo());
                }
            }
        }
            //更新所有发送成功的记录
            if (idList.size() > 0) {
                messageSynService.updateCardInfoFlag(idList);
                logger.info("本次完成上送的条数:" + idList.size());
                //插入完成之后,清空List数据
                idList.clear();
            }
    }

    public static void main(String[] args){

        for (int i = 0; i < threadCount; i++) {
            int num=i;
            Thread snedThread = new Thread(new Runnable() {
                int count = 380;
                int count1 = 380;
                @Override
                public void run() {
                    try {
                        while(true){
                            if(count==0&& num==1){
                                System.out.println("线程号"+num+"结束");
                                  break;
                             }else if(count>0&& num==1){
                                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())+"第"+num+"个线程"+"第"+count+"次");
                                count--;
                            }

                            if(count1==0 && num==0){
                                System.out.println("线程号"+num+"结束");
                                  break;
                            }else if(count1>0&& num==0){
                                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())+"第"+num+"个线程"+"第"+count1+"次");
                                count1--;
                            }
                              //如果有数据,调用发送接口

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            snedThread.setName("working thread");
            snedThread.start();
        }

    }









}
