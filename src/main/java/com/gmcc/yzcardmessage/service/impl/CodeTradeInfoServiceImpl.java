package com.gmcc.yzcardmessage.service.impl;

import com.gmcc.yzcardmessage.dao.slave.TradeInfoSlaveDao;
import com.gmcc.yzcardmessage.entity.AccountWallet;
import com.gmcc.yzcardmessage.service.CodeTradeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CodeTradeInfoServiceImpl implements CodeTradeInfoService {

    @Autowired
    TradeInfoSlaveDao tradeInfoSlaveDao;

    @Override
    public List<AccountWallet> getTradeInfo(){
        return tradeInfoSlaveDao.getTradeInfo();
    }

    @Override
    public void updateTradeFlag(List<String> idList) {
        tradeInfoSlaveDao.updateTradeFlag(idList);
    }

    @Override
    public void deleteTradeInfo() {
        tradeInfoSlaveDao.deleteTradeInfo();
    }

    @Override
    public void insertTradeInfo() {
        tradeInfoSlaveDao.insertTradeInfo();
    }

    @Override
    public void deleteTradeOldInfo() {
        tradeInfoSlaveDao.deleteTradeOldInfo();
    }

}
