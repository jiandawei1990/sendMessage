package com.gmcc.yzcardmessage.service.impl;

import com.gmcc.yzcardmessage.dao.master.TradeInfoMasterDao;
import com.gmcc.yzcardmessage.dao.slave.TradeInfoSlaveDao;
import com.gmcc.yzcardmessage.entity.*;
import com.gmcc.yzcardmessage.service.MessageSynService;
import com.gmcc.yzcardmessage.service.SendCodeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageSynServiceImpl implements MessageSynService {

    @Autowired
    TradeInfoMasterDao tradeInfoMasterDao;

    @Override
    public List<PosBusinfo> getVechileDeviceMappingData() {
        return tradeInfoMasterDao.getVechileDeviceMappingData();
    }

    /**
     * 获取线路数据
     * @return
     */
    @Override
    public List<BusLineinfo> getDeviceRouteMappingData() {
        return tradeInfoMasterDao.getDeviceRouteMappingData();
    }

    /**
     * 获取车辆数据
     * @return
     */
    @Override
    public List<Businfo> getVechileInfoData() {
        return tradeInfoMasterDao.getVechileInfoData();
    }

    /**
     * 获取城市卡数据
     * @param num
     * @return
     */
    @Override
    public List<Cardinfo> getIcCardData(int num) {
        return null;
    }

    @Override
    public void updateCardInfoFlag(List<String> ids) {

    }

}
