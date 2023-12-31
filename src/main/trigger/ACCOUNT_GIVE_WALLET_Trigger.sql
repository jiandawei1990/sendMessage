

-- ----------------------------
-- Triggers structure for table ACCOUNT_GIVE_WALLET
-- ----------------------------
DROP TRIGGER [dbo].[giveWallet_triger]
GO
CREATE TRIGGER [dbo].[giveWallet_triger]
ON [dbo].[ACCOUNT_GIVE_WALLET]
AFTER INSERT
AS
BEGIN
 INSERT INTO [GMCC_ACQUIRE_SYSTEM].[dbo].[ACCOUNT_GIVE_WALLET_gongan]
           ([SETTLE_DATE]
           ,[BATCH_NO]
           ,[UPLOAD_SEQUENCE]
           ,[CARD_NO]
           ,[CARD_SEQUENCE]
           ,[ORIGINAL_AMOUNT]
           ,[TRANSACT_TIME]
           ,[CARD_CATEGORY]
           ,[TRANSACT_AMOUNT]
           ,[CAST_COIN_AMOUNT]
           ,[CITY_CODE]
           ,[TRADE_CODE]
           ,[MAC]
           ,[CARD_ISSUER_CODE]
           ,[CARD_MAIN_TYPE]
           ,[CARD_SUB_TYPE]
           ,[TAC]
           ,[PSAM_TERMINAL_CODE]
           ,[PSAM_TERMINAL_TRANSACT_SEQUENCE]
           ,[PSAM_CODE]
           ,[KEY_VERSION]
           ,[KEY_ALGORITHM]
           ,[TRANSACT_TYPE]
           ,[COMPANY_CODE]
           ,[LINE_CODE]
           ,[BUS_CODE]
           ,[DEVICE_CODE]
           ,[DRIVER_CARD_NO]
           ,[TRANSFER_COUNT]
           ,[UP_DN]
           ,[GETON_STATION_NO]
           ,[INDIVIDUAL]
           ,[REG_TIME]
           ,[ERROR_CODE]
           ,[TO_TRANSFER]
           ,[RECONCILED]
           ,[ON_DUTY_TIME]
           ,[OFF_DUTY_TIME]
           ,[id]
           ,[FINISH]
          )
 
SELECT [SETTLE_DATE]
      ,[BATCH_NO]
      ,[UPLOAD_SEQUENCE]
      ,[CARD_NO]
      ,[CARD_SEQUENCE]
      ,[ORIGINAL_AMOUNT]
      ,[TRANSACT_TIME]
      ,[CARD_CATEGORY]
      ,[TRANSACT_AMOUNT]
      ,[CAST_COIN_AMOUNT]
      ,[CITY_CODE]
      ,[TRADE_CODE]
      ,[MAC]
      ,[CARD_ISSUER_CODE]
      ,[CARD_MAIN_TYPE]
      ,[CARD_SUB_TYPE]
      ,[TAC]
      ,[PSAM_TERMINAL_CODE]
      ,[PSAM_TERMINAL_TRANSACT_SEQUENCE]
      ,[PSAM_CODE]
      ,[KEY_VERSION]
      ,[KEY_ALGORITHM]
      ,[TRANSACT_TYPE]
      ,[COMPANY_CODE]
      ,[LINE_CODE]
      ,[BUS_CODE]
      ,[DEVICE_CODE]
      ,[DRIVER_CARD_NO]
      ,[TRANSFER_COUNT]
      ,[UP_DN]
      ,[GETON_STATION_NO]
      ,[INDIVIDUAL]
      ,[REG_TIME]
      ,[ERROR_CODE]
      ,[TO_TRANSFER]
      ,[RECONCILED]
      ,[ON_DUTY_TIME]
      ,[OFF_DUTY_TIME]
      ,replace(newID(),'-','')
      ,'0'
    from inserted
END

GO


CREATE TABLE [dbo].[Trade_Mx] (
[id] bigint NOT NULL IDENTITY(1,1) ,
[companyCode] varchar(20) NULL ,
[lineCode] varchar(20) NULL ,
[busCode] varchar(20) NULL ,
[deviceCode] varchar(20) NULL ,
[driverCardNo] varchar(20) NULL ,
[cardNo] varchar(20) NULL ,
[startStationName] varchar(10) NULL ,
[endStationName] varchar(10) NULL ,
[direction] varchar(10) NULL ,
[consumeType] varchar(10) NULL ,
[transactTime] varchar(30) NULL ,
[insertDate] datetime NULL ,
[FINISH] char(1) NULL ,
[FINISH_TIME] datetime NULL
)


GO
DBCC CHECKIDENT(N'[dbo].[Trade_Mx]', RESEED, 3071)
GO

-- ----------------------------
-- Indexes structure for table Trade_Mx
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table Trade_Mx
-- ----------------------------
ALTER TABLE [dbo].[Trade_Mx] ADD PRIMARY KEY ([id])
GO