package com.gmcc.yzcardmessage.entity;

/**
 * 扬州实体卡数据类型
 */
public class AccountWallet {

           private  String  id;
           private  String settleDate;                  //结算日期

            private  String  batchNo;

            private String  uploadSequence;

            private String  cardNo;                      //卡号

            private String cardSequence;

            private String ogiginalAmount;

            private  String transactTime;

            private String  cardCategory;

            private String transactAmount;

            private  String  castCoinAmount;

            private  String cityCode;

            private  String  tradeCode;

            private  String  mac;

            private String cardIssueCode;

            private String  cardMainType;

            private String cardSubType;

            private String  tac;

            private String psamTerminalCode;

            private  String psamTerminalTransactSequence;

            private String psamCode;

            private String keyVersion;

            private String keyAlgorithm;

            private String transatType;

            private String companyCode;       //公司号

            private String lineCode;          //线路号

            private String busCode;          //车辆号

            private String  deviceCode;      //设备号

            private String  deviceCardNo;     //司机卡号

            private String  transferCount;

            private String upDn;

            private String getonStationNo;

            private String  individual;

            private String regTime;

            private String  errorCode;

            private String toTransfer;

            private String reconciled;

            private String onDutyTime;

            private String offDutyTime;




    public String getBatchNo() {
        return batchNo;
    }

    public String getBusCode() {
        return busCode;
    }

    public String getTransactTime() {
        return transactTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public String getDeviceCardNo() {
        return deviceCardNo;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getLineCode() {
        return lineCode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getId() {
        return id;
    }


    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public String getCardSequence() {
        return cardSequence;
    }

    public String getOgiginalAmount() {
        return ogiginalAmount;
    }

    public String getUploadSequence() {
        return uploadSequence;
    }

    public String getCardCategory() {
        return cardCategory;
    }

    public String getCastCoinAmount() {
        return castCoinAmount;
    }

    public String getTransactAmount() {
        return transactAmount;
    }

    public void setCardCategory(String cardCategory) {
        this.cardCategory = cardCategory;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setCardSequence(String cardSequence) {
        this.cardSequence = cardSequence;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setCastCoinAmount(String castCoinAmount) {
        this.castCoinAmount = castCoinAmount;
    }

    public String getMac() {
        return mac;
    }

    public void setOgiginalAmount(String ogiginalAmount) {
        this.ogiginalAmount = ogiginalAmount;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public void setTransactAmount(String transactAmount) {
        this.transactAmount = transactAmount;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public void setTransactTime(String transactTime) {
        this.transactTime = transactTime;
    }

    public void setUploadSequence(String uploadSequence) {
        this.uploadSequence = uploadSequence;
    }

    public void setDeviceCardNo(String deviceCardNo) {
        this.deviceCardNo = deviceCardNo;
    }

    public String getCardIssueCode() {
        return cardIssueCode;
    }

    public String getCardMainType() {
        return cardMainType;
    }

    public String getCardSubType() {
        return cardSubType;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public String getPsamCode() {
        return psamCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getGetonStationNo() {
        return getonStationNo;
    }

    public void setCardIssueCode(String cardIssueCode) {
        this.cardIssueCode = cardIssueCode;
    }

    public String getKeyAlgorithm() {
        return keyAlgorithm;
    }

    public String getPsamTerminalCode() {
        return psamTerminalCode;
    }

    public void setCardMainType(String cardMainType) {
        this.cardMainType = cardMainType;
    }

    public String getKeyVersion() {
        return keyVersion;
    }

    public void setCardSubType(String cardSubType) {
        this.cardSubType = cardSubType;
    }

    public String getPsamTerminalTransactSequence() {
        return psamTerminalTransactSequence;
    }

    public void setTransatType(String transatType) {
        this.transatType = transatType;
    }

    public String getTransatType() {
        return transatType;
    }
}


