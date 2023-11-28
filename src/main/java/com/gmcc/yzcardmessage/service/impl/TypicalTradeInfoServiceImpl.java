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
    public List<AccountWallet> getTradeInfo(){
        return tradeInfoMasterDao.getTradeInfo();
    }

    @Override
    public List<AccountWallet> getAlipayTradeInfo(){
        return tradeInfoMasterDao.getAlipayTradeInfo();
    }

    @Override
    public List<AccountWallet> getQrtTradeInfo(){
        return tradeInfoMasterDao.getQrtTradeInfo();
    }

    @Override
    public void updateTradeFlag(List<String> idList) {
        tradeInfoMasterDao.updateTradeFlag(idList);
    }

    @Override
    public void updateAlipayTradeFlag(List<String> idList) {
        tradeInfoMasterDao.updateAlipayTradeFlag(idList);
    }

    @Override
    public void updateQrtTradeFlag(List<String> idList) {
        tradeInfoMasterDao.updateQrtTradeFlag(idList);
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

    @Override
    public void deleteAlipayTradeInfo() {
        tradeInfoMasterDao.deleteAlipayTradeInfo();
    }

    @Override
    public void insertAlipayTradeInfo() {
        tradeInfoMasterDao.insertAlipayTradeInfo();
    }

    @Override
    public void deleteAlipayTradeOldInfo() {
        tradeInfoMasterDao.deleteAlipayTradeOldInfo();
    }

    @Override
    public void deleteQrtTradeInfo() {
        tradeInfoMasterDao.deleteQrtTradeInfo();
    }

    @Override
    public void insertQrtTradeInfo() {
        tradeInfoMasterDao.insertQrtTradeInfo();
    }

    @Override
    public void deleteQrtTradeOldInfo() {
        tradeInfoMasterDao.deleteQrtTradeOldInfo();
    }

}
