package com.gmcc.yzcardmessage.service.impl;


import com.gmcc.yzcardmessage.service.TypicalTradeInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@EnableScheduling
@Transactional
public class DeleteTradeTask {

    private static Logger logger = LoggerFactory.getLogger(DeleteTradeTask.class);

    @Autowired
    private TypicalTradeInfoService typicalTradeInfoService;

    /**
     * 删除6天前的交易数据
     */
    @Scheduled(cron = "${generator.deleteTradeInfo.quartzConfiguration.cronExpression}")
    public void deleteTradeBeforeWeekData(){
        logger.info("主数据库定时任务");
        logger.info("开始执行定时任务:");
//        logger.info("开始执行删除6天数据的定时任务");
//        typicalTradeInfoService.deleteAlipayTradeInfo();
//
//        logger.info("数据转储到Bak表");
//        typicalTradeInfoService.insertAlipayTradeInfo();

//        logger.info("删除正式表之前的数据");
//        typicalTradeInfoService.deleteAlipayTradeOldInfo();
        logger.info("开始执行删除6天数据的定时任务");
        typicalTradeInfoService.deleteQrtTradeInfo();

        logger.info("数据转储到Bak表");
        typicalTradeInfoService.insertQrtTradeInfo();

        logger.info("删除正式表之前的数据");
        typicalTradeInfoService.deleteQrtTradeOldInfo();
        logger.info("定时任务执行完成");
    }

}
