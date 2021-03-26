package com.gmcc.yzcardmessage.service.impl;



import com.gmcc.yzcardmessage.dao.master.TradeInfoMasterDao;
import com.gmcc.yzcardmessage.entity.AccountWallet;
import com.gmcc.yzcardmessage.service.TypicalTradeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypicalTradeInfoServiceImpl implements TypicalTradeInfoService {



    @Autowired
    TradeInfoMasterDao tradeInfoMasterDao;




    @Override
    public List<AccountWallet> getTradeInfo() {
        return tradeInfoMasterDao.getTradeInfo();
    }

    @Override
    public void updateTradeFlag(List<String> idList) {
        tradeInfoMasterDao.updateTradeFlag(idList);
    }

    @Override
    public void deleteTradeInfo() {
        tradeInfoMasterDao.deleteTradeInfo();
    }

    @Override
    public void insertTradeInfo() {
        tradeInfoMasterDao.insertTradeInfo();
    }

    @Override
    public void deleteTradeOldInfo() {
        tradeInfoMasterDao.deleteTradeOldInfo();
    }


}
