package com.gmcc.yzcardmessage.service;

import com.gmcc.yzcardmessage.entity.*;

import java.util.List;

public interface MessageSynService {

    //车辆机具映射关系
    public List<PosBusinfo>  getVechileDeviceMappingData();

    //车载机线路映射信息
    public List<BusLineinfo>  getDeviceRouteMappingData();

    //车辆信息
    public List<Businfo> getVechileInfoData();

    //IC卡信息Cardinfo
    public List<Cardinfo> getIcCardData(int num);

    //IC卡数据上送后更新
    public void updateCardInfoFlag(List<String> ids);

}
