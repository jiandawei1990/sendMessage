//package com.gmcc.yzcardmessage.service.impl;
//
//import com.gmcc.yzcardmessage.service.CodeTradeInfoService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//@EnableScheduling
//@Transactional
//public class DeleteCodeTask {
//
//    private static Logger logger = LoggerFactory.getLogger(DeleteCodeTask.class);
//
//    @Autowired
//    private CodeTradeInfoService codeTradeInfoService;
//
//
//    /**
//     * 删除6天前的交易数据
//     */
//    @Scheduled(cron = "${generator.deleteCodeTradeInfo.quartzConfiguration.cronExpression}")
//    public void deleteTradeBeforeWeekData(){
//
//        logger.info("从数据库定时任务");
//        logger.info("开始执行定时任务:");
//        logger.info("开始执行删除6天数据的定时任务");
//        codeTradeInfoService.deleteTradeInfo();
//
//        logger.info("数据转储到Bak表");
//        codeTradeInfoService.insertTradeInfo();
//
//        logger.info("删除正式表之前的数据");
//        codeTradeInfoService.deleteTradeOldInfo();
//        logger.info("定时任务执行完成");
//    }
//
//
//
//}
