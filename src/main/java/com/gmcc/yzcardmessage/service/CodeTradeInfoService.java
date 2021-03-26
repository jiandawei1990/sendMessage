package com.gmcc.yzcardmessage.service;

import com.gmcc.yzcardmessage.entity.AccountWallet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 二维码数据
 */
public interface CodeTradeInfoService {

    //获取二维码交易数据每次2000条
    public List<AccountWallet> getTradeInfo();


    //更新交易标志位上传完成
    public void updateTradeFlag(@Param("idList") List<String> idList);


    //删除7天前的数据
    public void deleteTradeInfo();


    //插入交易数据
    public void insertTradeInfo();


    //删除正式表数据
    public  void deleteTradeOldInfo();


}
