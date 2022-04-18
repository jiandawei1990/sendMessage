package com.gmcc.yzcardmessage.dao.slave;


import com.gmcc.yzcardmessage.entity.AccountWallet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 * 获取实体卡交易数据
 */

@Mapper
public interface TradeInfoSlaveDao{


    //获取实体卡交易数据每次2000条
    public List<AccountWallet>  getTradeInfo();

    //更新交易标志位上传完成
    public void updateTradeFlag(@Param("idList") List<String> idList);

    //删除7天前的交易数据
    public void deleteTradeInfo();

    //昨天之前的数据转储到Bak表
    public  void insertTradeInfo();


    //删除主表历史数据
    public void deleteTradeOldInfo();



}
