package com.gmcc.yzcardmessage.entity;

import lombok.Data;

/**
 * 卡信息
 */
@Data
public class Cardinfo {

    public String cardNo;
    public String cardCSN;
    public String userBirthday;
    public String userSex;
    public String cardType;

}
