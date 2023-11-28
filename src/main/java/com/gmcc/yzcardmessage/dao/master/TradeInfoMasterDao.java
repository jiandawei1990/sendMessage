package com.gmcc.yzcardmessage.dao.master;


import com.gmcc.yzcardmessage.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 * 获取实体卡交易数据
 */

@Mapper
public interface TradeInfoMasterDao {

    //获取实体卡交易数据每次2000条
    public List<AccountWallet>  getTradeInfo();

    //获取实体卡交易数据每次2000条给支付宝
    public List<AccountWallet>  getAlipayTradeInfo();

    //获取二维码交易数据每次2000条给支付宝
    public List<AccountWallet>  getQrtTradeInfo();

    //更新交易标志位上传完成
    public void updateTradeFlag(@Param("idList") List<String> idList);

    //更新交易标志位上传完成
    public void updateAlipayTradeFlag(@Param("idList") List<String> idList);

    //更新交易标志位上传完成
    public void updateQrtTradeFlag(@Param("idList") List<String> idList);

    //删除7天前的交易数据
    public void deleteTradeInfo();

    //昨天之前的数据转储到Bak表
    public  void insertTradeInfo();


    //删除主表历史数据
    public void deleteAlipayTradeOldInfo();

    //删除7天前的交易数据
    public void deleteAlipayTradeInfo();

    //昨天之前的数据转储到Bak表
    public  void insertAlipayTradeInfo();


    //删除主表历史数据
    public void deleteQrtTradeOldInfo();

    //删除7天前的交易数据
    public void deleteQrtTradeInfo();

    //昨天之前的数据转储到Bak表
    public  void insertQrtTradeInfo();


    //删除主表历史数据
    public void deleteTradeOldInfo();

    //查询车辆机具映射关系
    public List<PosBusinfo> getVechileDeviceMappingData();

    //车载机线路映射关系
    public List<BusLineinfo> getDeviceRouteMappingData();

    //车辆信息
    public List<Businfo> getVechileInfoData();


    //IC卡信息Cardinfo
    public List<Cardinfo> getIcCardData(int num);

    //IC卡数据上送后更新
    public void updateCardInfoFlag(List<String> ids);

}
