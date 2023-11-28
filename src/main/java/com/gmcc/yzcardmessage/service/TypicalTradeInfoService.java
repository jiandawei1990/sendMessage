package com.gmcc.yzcardmessage.service;


import com.gmcc.yzcardmessage.entity.AccountWallet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TypicalTradeInfoService {

    //获取实体卡交易数据每次2000条
    public List<AccountWallet> getTradeInfo();

    //获取实体卡交易数据每次2000条给支付宝
    public List<AccountWallet> getAlipayTradeInfo();

    //获取二维码交易数据每次2000条给支付宝
    public List<AccountWallet> getQrtTradeInfo();

    //更新交易标志位上传完成
    public void updateTradeFlag(@Param("idList") List<String> idList);

    //更新实体卡交易标志位上传完成
    public void updateAlipayTradeFlag(@Param("idList") List<String> idList);

    //更新二维码交易标志位上传完成
    public void updateQrtTradeFlag(@Param("idList") List<String> idList);

    //删除7天前的数据
    public void deleteTradeInfo();

    //插入交易数据
    public void insertTradeInfo();

    //删除正式表数据
    public  void deleteTradeOldInfo();

    //删除7天前的数据
    public void deleteAlipayTradeInfo();

    //插入交易数据
    public void insertAlipayTradeInfo();

    //删除正式表数据
    public  void deleteAlipayTradeOldInfo();

    //删除7天前的数据
    public void deleteQrtTradeInfo();

    //插入交易数据
    public void insertQrtTradeInfo();

    //删除正式表数据
    public  void deleteQrtTradeOldInfo();

}
