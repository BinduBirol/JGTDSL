ALTER TABLE DB_PGCL.BILL_NON_METERED
 DROP PRIMARY KEY CASCADE;

DROP TABLE DB_PGCL.BILL_NON_METERED CASCADE CONSTRAINTS;

CREATE TABLE DB_PGCL.BILL_NON_METERED
(
  BILL_ID                  VARCHAR2(12 BYTE)    NOT NULL,
  BILL_MONTH               NUMBER               NOT NULL,
  BILL_YEAR                NUMBER               NOT NULL,
  CUSTOMER_ID              VARCHAR2(12 BYTE)    NOT NULL,
  CUSTOMER_NAME            VARCHAR2(100 BYTE)   NOT NULL,
  PROPRIETOR_NAME          VARCHAR2(100 BYTE),
  CUSTOMER_CATEGORY        VARCHAR2(2 BYTE)     NOT NULL,
  CUSTOMER_CATEGORY_NAME   VARCHAR2(50 BYTE)    NOT NULL,
  CATEGORY_TYPE            VARCHAR2(10 BYTE)    NOT NULL,
  AREA_ID                  VARCHAR2(2 BYTE)     NOT NULL,
  AREA_NAME                VARCHAR2(50 BYTE)    NOT NULL,
  ADDRESS                  CLOB,
  SINGLE_BURNER_QNT        NUMBER,
  SINGLE_BURNER_RATE       NUMBER,
  SINGLE_BURNER_TARIFF_ID  NUMBER,
  DOUBLE_BURNER_QNT        NUMBER,
  DOUBLE_BURNER_RATE       NUMBER,
  DOUBLE_BURNER_TARIFF_ID  NUMBER,
  ACTUAL_BILLED_AMOUNT            NUMBER,
  COLLECTED_BILLED_AMOUNT NUMBER,
  ACTUAL_SURCHARGE         NUMBER,
  COLLECTED_SURCHARGE      NUMBER,
  ACTUAL_PAYABLE_AMOUNT           NUMBER,
  COLLECTED_PAYABLE_AMOUNT           NUMBER,
  PB                       NUMBER,
  VAT                      NUMBER,
  SD                       NUMBER,
  TOTAL_CONSUMPTION        NUMBER,
  BILL_GENERATION_DATE     DATE,
  DUE_DATE                 DATE,
  FF_QUOTA                 CHAR(1 BYTE),
  PREPARED_ON              DATE,
  PREPARED_BY              VARCHAR2(12 BYTE),
  STATUS                   NUMBER,
  COLLECTED_AMOUNT         NUMBER
)
LOB (ADDRESS) STORE AS (
  TABLESPACE  SYSTEM
  ENABLE      STORAGE IN ROW
  CHUNK       8192
  RETENTION
  NOCACHE
  LOGGING
      STORAGE    (
                  INITIAL          64K
                  NEXT             1M
                  MINEXTENTS       1
                  MAXEXTENTS       UNLIMITED
                  PCTINCREASE      0
                  FREELISTS        1
                  FREELIST GROUPS  1
                  BUFFER_POOL      DEFAULT
                  FLASH_CACHE      DEFAULT
                  CELL_FLASH_CACHE DEFAULT
                 ))
TABLESPACE SYSTEM
RESULT_CACHE (MODE DEFAULT)
PCTUSED    40
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE UNIQUE INDEX DB_PGCL.BILL_NON_METERED_PK ON DB_PGCL.BILL_NON_METERED
(BILL_ID)
LOGGING
TABLESPACE SYSTEM
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
NOPARALLEL;


CREATE INDEX DB_PGCL.IND1_BNM ON DB_PGCL.BILL_NON_METERED
(CUSTOMER_ID, BILL_MONTH, BILL_YEAR)
LOGGING
TABLESPACE SYSTEM
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
NOPARALLEL;


ALTER TABLE DB_PGCL.BILL_NON_METERED ADD (
  CONSTRAINT BILL_NON_METERED_PK
  PRIMARY KEY
  (BILL_ID)
  USING INDEX DB_PGCL.BILL_NON_METERED_PK
  ENABLE VALIDATE);
  
DROP TABLE DB_PGCL.BILL_COLLECTION_NON_METERED CASCADE CONSTRAINTS;
CREATE TABLE DB_PGCL.BILL_COLLECTION_NON_METERED
(
  COLLECTION_ID      NUMBER                     NOT NULL,
  CUSTOMER_ID        VARCHAR2(12 BYTE)          NOT NULL,
  BILL_ID            VARCHAR2(10 BYTE)          NOT NULL,
  BANK_ID            VARCHAR2(2 BYTE)           NOT NULL,
  BRANCH_ID          VARCHAR2(4 BYTE)           NOT NULL,
  ACCOUNT_NO         VARCHAR2(20 BYTE)          NOT NULL,
  COLLECTION_DATE    DATE                       NOT NULL,
  COLLECTED_BILL_AMOUNT  NUMBER                     NOT NULL,
  COLLECTED_SURCHARGE_AMOUNT  NUMBER                     NOT NULL,
  TOTAL_COLLECTED_AMOUNT  NUMBER                     NOT NULL,
  REMARKS            VARCHAR2(300 BYTE),
  COLLECED_BY        VARCHAR2(50 BYTE),
  INSERTED_ON        DATE
)
TABLESPACE SYSTEM
RESULT_CACHE (MODE DEFAULT)
PCTUSED    40
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

COMMENT ON COLUMN DB_PGCL.BILL_COLLECTION_NON_METERED.COLLECTION_ID IS 'Autogenerated Collection Id';

COMMENT ON COLUMN DB_PGCL.BILL_COLLECTION_NON_METERED.BILL_ID IS 'Bill Id';

COMMENT ON COLUMN DB_PGCL.BILL_COLLECTION_NON_METERED.BANK_ID IS 'Bank Id';

COMMENT ON COLUMN DB_PGCL.BILL_COLLECTION_NON_METERED.BRANCH_ID IS 'Branch Id';

COMMENT ON COLUMN DB_PGCL.BILL_COLLECTION_NON_METERED.ACCOUNT_NO IS 'Account Number';

COMMENT ON COLUMN DB_PGCL.BILL_COLLECTION_NON_METERED.COLLECTION_DATE IS 'Collection Date';

COMMENT ON COLUMN DB_PGCL.BILL_COLLECTION_NON_METERED.REMARKS IS 'Remarks';

COMMENT ON COLUMN DB_PGCL.BILL_COLLECTION_NON_METERED.COLLECED_BY IS 'Collected By';

COMMENT ON COLUMN DB_PGCL.BILL_COLLECTION_NON_METERED.INSERTED_ON IS 'Inserted On';



CREATE INDEX DB_PGCL.BC_NM_IND1 ON DB_PGCL.BILL_COLLECTION_NON_METERED
(CUSTOMER_ID)
LOGGING
TABLESPACE SYSTEM
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
NOPARALLEL;


CREATE INDEX DB_PGCL.BC_NM_IND2 ON DB_PGCL.BILL_COLLECTION_NON_METERED
(BANK_ID, BRANCH_ID, ACCOUNT_NO)
LOGGING
TABLESPACE SYSTEM
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
NOPARALLEL;


CREATE INDEX DB_PGCL.BC_NM_IND3 ON DB_PGCL.BILL_COLLECTION_NON_METERED
(BILL_ID)
LOGGING
TABLESPACE SYSTEM
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
NOPARALLEL;


CREATE UNIQUE INDEX DB_PGCL.BILL_COLLECTION_NON_METERED_PK ON DB_PGCL.BILL_COLLECTION_NON_METERED
(COLLECTION_ID)
LOGGING
TABLESPACE SYSTEM
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            FREELISTS        1
            FREELIST GROUPS  1
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
NOPARALLEL;


ALTER TABLE DB_PGCL.BILL_COLLECTION_NON_METERED ADD (
  CONSTRAINT BILL_COLLECTION_NON_METERED_PK
  PRIMARY KEY
  (COLLECTION_ID)
  USING INDEX DB_PGCL.BILL_COLLECTION_NON_METERED_PK
  ENABLE VALIDATE);
  
ALTER TABLE DB_PGCL.BILL_NON_METERED
MODIFY(ACTUAL_BILLED_AMOUNT  NOT NULL);


ALTER TABLE DB_PGCL.BILL_NON_METERED
MODIFY(COLLECTED_BILLED_AMOUNT  DEFAULT 0);

ALTER TABLE DB_PGCL.BILL_NON_METERED
MODIFY(ACTUAL_SURCHARGE  DEFAULT 0);

ALTER TABLE DB_PGCL.BILL_NON_METERED
MODIFY(COLLECTED_SURCHARGE  DEFAULT 0);

ALTER TABLE DB_PGCL.BILL_NON_METERED
MODIFY(ACTUAL_PAYABLE_AMOUNT  NOT NULL);


ALTER TABLE DB_PGCL.BILL_NON_METERED
MODIFY(COLLECTED_PAYABLE_AMOUNT  DEFAULT 0);

------------ Procedure Changes

CREATE OR REPLACE PROCEDURE DB_PGCL.GenerateBill_NonMetered(

   iBillFor                   IN   VARCHAR2,
   iCustomerId                IN   VARCHAR2,
   iCategoryId                IN   VARCHAR2,  
   iAreaId                    IN   VARCHAR2,   
   iBillMonth                 IN   NUMBER,  
   iBillYear                  IN   NUMBER,
   iBillGenerationDate        IN   VARCHAR2, 
   iUserId                    IN   VARCHAR2, 
   iRemarks                   IN   VARCHAR2,  
   iReprocess                 IN   VARCHAR2,   
   oResponse                  OUT  NUMBER,
   oRespMsg                   OUT  VARCHAR2
)

IS

    off_cursor sys_refcursor;
    tSingleBurnerQnt     NUMBER := 0;
    tDoubleBurnerQnt     NUMBER := 0;
    tSingleBurnerPrice   NUMBER := 0;
    tDoubleBurnerPrice   NUMBER := 0;
    tBillMonthYear       VARCHAR2(15);
    
    tConnectionMonth Number;
    tConnectionYear Number;
    tConnectionDate Date;
    
    where_clause varchar2(100);
    tAreaId  varchar2(2);
    tLockStatus number;
    billId   number;
    tBillRate number;
    tPDC_SB   number; --tPerDayConsumption single burner (Day)
    tPMC_SB   number; --tPeMonthConsumption single burner (Month)
    tPDC_DB   number;--tPerDayConsumption double burner; (Day)
    tPMC_DB   number;--tPerMonthConsumption double burner; (Month)
    tTmpCount number;
    tBillAmount number;
    tPayableAmount number;
    tAmount number;
    tCount number;
    tDays number;
    tSingleBurnerConsumption number;
    tDoubleBurnerConsumption number;
    tBillId Number;
    

    tTotalOffDays number;
    tFullBillDays  number;
    tHalfBillDays  number;
    tTotalDaysInBillingMonth number;

    d_days number;
    d_increase number;
    d_reconnection number;
    d_burner number;
    d_disconn_type number;
    d_disconn_cause number;
    d_disconn_desc  varchar2(25);

    TYPE customer_id_t IS TABLE OF MVIEW_CUSTOMER_INFO.CUSTOMER_ID%TYPE
     INDEX BY PLS_INTEGER; 
    TYPE full_name_t IS TABLE OF MVIEW_CUSTOMER_INFO.FULL_NAME%TYPE
     INDEX BY PLS_INTEGER;
    TYPE proprietor_t IS TABLE OF MVIEW_CUSTOMER_INFO.PROPRIETOR_NAME%TYPE
     INDEX BY PLS_INTEGER;     
    TYPE category_id_t IS TABLE OF MVIEW_CUSTOMER_INFO.CATEGORY_ID%TYPE
     INDEX BY PLS_INTEGER;
    TYPE category_name_t IS TABLE OF MVIEW_CUSTOMER_INFO.CATEGORY_NAME%TYPE
     INDEX BY PLS_INTEGER; 
    TYPE category_type_t IS TABLE OF MVIEW_CUSTOMER_INFO.CATEGORY_TYPE%TYPE
     INDEX BY PLS_INTEGER; 
    TYPE area_id_t IS TABLE OF MVIEW_CUSTOMER_INFO.AREA_ID%TYPE
     INDEX BY PLS_INTEGER;
    TYPE area_name_t IS TABLE OF MVIEW_CUSTOMER_INFO.AREA_NAME%TYPE
     INDEX BY PLS_INTEGER;     
    TYPE address_t IS TABLE OF MVIEW_CUSTOMER_INFO.ADDRESS%TYPE
     INDEX BY PLS_INTEGER;
    TYPE single_burner_qnt_t IS TABLE OF MVIEW_CUSTOMER_INFO.SINGLE_BURNER_QNT%TYPE
     INDEX BY PLS_INTEGER; 
    TYPE double_burner_qnt_t IS TABLE OF MVIEW_CUSTOMER_INFO.DOUBLE_BURNER_QNT%TYPE
     INDEX BY PLS_INTEGER;   
     TYPE freedoom_fighter_t IS TABLE OF MVIEW_CUSTOMER_INFO.FREEDOM_FIGHTER%TYPE
     INDEX BY PLS_INTEGER;   
    TYPE holiday_t IS TABLE OF HOLIDAYS.HOLIDAY_DATE%TYPE
     INDEX BY PLS_INTEGER; 
    TYPE cachePayDate_t IS TABLE OF Date 
     INDEX BY VARCHAR2(64);  
    l_customer_id_t   customer_id_t;
    l_full_name_t   full_name_t;
    l_proprietor_t   proprietor_t;
    l_category_id_t   category_id_t;
    l_category_name_t   category_name_t;
    l_category_type_t   category_type_t;
    l_area_id_t   area_id_t;
    l_area_name_t   area_name_t;
    l_address_t   address_t;
    l_single_burner_qnt_t   single_burner_qnt_t;
    l_double_burner_qnt_t   double_burner_qnt_t;
    l_freedom_fighter_t     freedoom_fighter_t;
    l_holiday_t holiday_t;
    l_cachePayDate_t cachePayDate_t;
    l_start number;
    l_end number;
    l_diff number;
             
    ffDiscount number;
     
     initialpaydate  DATE; 
    pay_date_holder Date;

BEGIN

      oResponse:=0;
      tTotalOffDays:=0;
      tFullBillDays:=0;
      tHalfBillDays:=0;
      tSingleBurnerConsumption :=0;
      tDoubleBurnerConsumption :=0;
      l_start := dbms_utility.get_time ;
       
     tBillMonthYear :=iBillMonth||'-'||iBillYear; 
     If(iBillFor='individual_customer') Then 
       where_clause := ' and Customer_Id='''||iCustomerId||'''';
       Select Area into tAreaId from Customer Where customer_id= iCustomerId;
     ELSIF(iBillFor='category_wise') Then 
       where_clause := ' and category_id='''||iCategoryId || ''' and area_id= '''|| iAreaId||'''';  
       tAreaId:=iAreaId;     
     ELSIF(iBillFor='area_wise') Then 
       where_clause := ' and  area_id= '''|| iAreaId||''''; 
       tAreaId:=iAreaId;      
     ELSE
       oResponse:=-1;
       DBMS_OUTPUT.PUT_LINE ('Invalid iBillFor value');
       return;
     End If;
     
     
     Select status into tLockStatus From BILLING_SEMAPHORE Where AREA_ID=tAreaId and ISMETERED=0;
     
     If(tLockStatus=1) Then
        oResponse:=2;
        oRespMsg:='Failed to Take the lock control';
        return;
     End If;
     
     MERGE INTO BILLING_SEMAPHORE BS
        USING (SELECT tAreaId Area_id,1 IsMetered FROM dual ) BS1
        ON (BS.AREA_ID = BS1.AREA_ID and BS.ISMETERED = BS1.ISMETERED)
        WHEN MATCHED THEN 
        UPDATE SET BS.STATUS=1
        WHEN NOT MATCHED THEN
             INSERT (AREA_ID,ISMETERED,PROCESSED_BY,STATUS)
             VALUES(tAreaId,0,iUserId,1);

    dbms_output.put_line(getTimeDiff(l_start));
    
    EXECUTE IMMEDIATE 
    'select HOLIDAY_DATE from HOLIDAYS where HOLIDAY_DATE BETWEEN to_date('''|| 
    iBillGenerationDate||''',''DD-MM-YYYY'') AND to_date('''||iBillGenerationDate|| 
    ''',''DD-MM-YYYY'')+200'bulk collect INTO l_holiday_t; 
            
    EXECUTE IMMEDIATE
        'SELECT CUSTOMER_ID,FULL_NAME,PROPRIETOR_NAME,CATEGORY_ID,CATEGORY_NAME,CATEGORY_TYPE,AREA_ID,AREA_NAME,ADDRESS,SINGLE_BURNER_QNT,DOUBLE_BURNER_QNT,FREEDOM_FIGHTER  FROM MVIEW_CUSTOMER_INFO Where ismetered=0 '|| where_clause
    BULK COLLECT INTO l_customer_id_t,l_full_name_t,l_proprietor_t,l_category_id_t,l_category_name_t,l_category_type_t,l_area_id_t,l_area_name_t,l_address_t,l_single_burner_qnt_t,l_double_burner_qnt_t,l_freedom_fighter_t;
    
    FOR indx IN 1 .. l_customer_id_t.COUNT
     LOOP
     
           dbms_output.put_line(getTimeDiff(l_start));
           ffDiscount:=0;
           If(l_freedom_fighter_t(indx)='Y') Then ffDiscount:=tDoubleBurnerPrice; End If;
           
            
      
            Select  Count(Bill_Id) InTo tCount From BILL_NON_METERED Where Customer_Id=l_customer_id_t(indx) and Bill_Month=iBillMonth and Bill_Year=iBillYear;
            If(tCount!=0 and iReprocess='Y')
            Then               
               Select  BILL_ID InTo tBillId From BILL_NON_METERED Where Customer_Id=l_customer_id_t(indx) and Bill_Month=iBillMonth and Bill_Year=iBillYear;
               Delete BILL_NON_METERED_DTL Where Bill_Id=tBillId;
               Delete BILL_NON_METERED Where Bill_Id=tBillId;
               Delete CUSTOMER_LEDGER Where Customer_Id=l_customer_id_t(indx) and  Bill_Id =tBillId;
               Delete BANK_ACCOUNT_LEDGER Where Customer_Id=l_customer_id_t(indx) and  Trans_Type =1 and REF_ID=tBillId;
            End If;
             
            If(tCount!=0 and iReprocess='N')
            Then
              exit;
            End If;
              
            Select SQN_BILL_NONMETERED.nextval into billId from dual;  
            
            dbms_output.put_line(getTimeDiff(l_start));
            
             
          
           
            
                    BEGIN
                         pay_date_holder:=l_cachePayDate_t(to_char(to_date('21-'||tBillMonthYear,'dd-MM-YYYY HH24:MI:SS')));
                      EXCEPTION
                        WHEN no_data_found 
                         THEN
                            initialpaydate := to_date('21-'||tBillMonthYear,'dd-MM-YYYY HH24:MI:SS'); 
                            pay_date_holder:=initialpaydate;
                              FOR indx IN 1 .. l_holiday_t.count LOOP           
                                    IF pay_date_holder=l_holiday_t(indx)THEN
                                        pay_date_holder:=pay_date_holder+1;
                                    END IF;   
                              END LOOP;                          
                                                 
                           l_cachePayDate_t(to_char(iBillGenerationDate)||to_char(21)):=pay_date_holder;
                    END;
                    
  
            /** Price Rate Calculation **/
            Select Price into tSingleBurnerPrice  from MST_TARIFF Where Meter_Status=0 And Burner_Category=1 And Customer_Category_Id=l_category_id_t(indx) 
            And Effective_From<=to_date('01-'||tBillMonthYear,'dd-MM-YYYY HH24:MI:SS')
            And (Effective_To is Null or Effective_To>=to_date('01-'||tBillMonthYear,'dd-MM-YYYY HH24:MI:SS'));
            
            Select Price into tDoubleBurnerPrice  from MST_TARIFF Where Meter_Status=0 And Burner_Category=2 And Customer_Category_Id=l_category_id_t(indx) 
            And Effective_From<=to_date('01-'||tBillMonthYear,'dd-MM-YYYY HH24:MI:SS')
            And (Effective_To is Null or Effective_To>=to_date('01-'||tBillMonthYear,'dd-MM-YYYY HH24:MI:SS'));
            
            Select Price into tBillRate  from MST_TARIFF Where Meter_Status=1 And Customer_Category_Id=l_category_id_t(indx) 
            And Effective_From<=to_date('01-'||tBillMonthYear,'dd-MM-YYYY HH24:MI:SS')
            And (Effective_To is Null or Effective_To>=to_date('01-'||tBillMonthYear,'dd-MM-YYYY HH24:MI:SS'));
            
            SELECT CAST(to_char(LAST_DAY(to_date('01-'||iBillMonth||'-'||iBillYear,'dd-MM-YYYY')),'dd') AS INT) into tTotalDaysInBillingMonth FROM dual;
            
            
            tPMC_SB:=tSingleBurnerPrice/tBillRate;  -- Per Month Consumption (Single Burner)
            tPDC_SB:=tPMC_SB/tTotalDaysInBillingMonth;                    -- Per Day Consumption
            
            tPMC_DB:=tDoubleBurnerPrice/tBillRate;  -- Per Month Consumption (Double Burner)
            tPDC_DB:=tPMC_DB/tTotalDaysInBillingMonth;                    -- Per Day Consumption

            dbms_output.put_line(getTimeDiff(l_start));
    
            NonMeteredBill_Helper(billId,l_customer_id_t(indx),iBillMonth,iBillYear);

            dbms_output.put_line(getTimeDiff(l_start));
    
            Select Count(Bill_Id) into tTmpCount From BILL_NON_METERED_DTL Where Bill_Id=billId;        
         
            If(tTmpCount=0) Then
            
                Select to_number(to_char(Connection_Date,'MM')),to_number(to_char(Connection_Date,'YYYY')),Connection_Date into  tConnectionMonth,tConnectionYear,tConnectionDate From CUSTOMER_CONNECTION Where Customer_Id=l_customer_id_t(indx);
                If(tConnectionMonth=iBillMonth and tConnectionYear=iBillYear and to_number(to_char(tConnectionDate,'dd'))!=1) Then
                
                    SELECT LAST_DAY(tConnectionDate)-tConnectionDate into tDays from dual;
                    tDays:=tDays+1;
                    tAmount  :=l_single_burner_qnt_t(indx)*tDays*tPDC_SB*tBillRate+l_double_burner_qnt_t(indx)*tDays*tPDC_DB*tBillRate;
                    tBillAmount :=tAmount;
                    If(l_freedom_fighter_t(indx)='Y') Then
                        tPayableAmount := tAmount-tDays*tPDC_DB*tBillRate;
                    Else
                        tPayableAmount := tAmount;
                    End If;
                    
                    
                    tSingleBurnerConsumption :=tPDC_SB*tDays;
                    tDoubleBurnerConsumption := tPDC_DB*tDays;
           
                Else
                   
                    
                    tAmount :=l_single_burner_qnt_t(indx)*tSingleBurnerPrice+l_double_burner_qnt_t(indx)*tDoubleBurnerPrice;
                    tBillAmount :=tAmount;
                    tPayableAmount := tAmount-ffDiscount;
                    
                               
                    tSingleBurnerConsumption :=tPMC_SB;
                    tDoubleBurnerConsumption := tPMC_DB;      
                
                End If;
                
                tBillAmount:=round(tBillAmount);
                tPayableAmount:=round(tPayableAmount);
                
                Insert Into BILL_NON_METERED(BILL_ID,BILL_MONTH,BILL_YEAR,
                CUSTOMER_ID,CUSTOMER_NAME,PROPRIETOR_NAME,CUSTOMER_CATEGORY,CUSTOMER_CATEGORY_NAME,CATEGORY_TYPE,AREA_ID,AREA_NAME,ADDRESS,SINGLE_BURNER_QNT,SINGLE_BURNER_RATE,
                DOUBLE_BURNER_QNT,DOUBLE_BURNER_RATE,ACTUAL_BILLED_AMOUNT,ACTUAL_PAYABLE_AMOUNT,STATUS,TOTAL_CONSUMPTION,BILL_GENERATION_DATE,DUE_DATE)
                Values(billId,iBillMonth,iBillYear,l_customer_id_t(indx),l_full_name_t(indx),l_proprietor_t(indx),l_category_id_t(indx),l_category_name_t(indx),l_category_type_t(indx),l_area_id_t(indx),l_area_name_t(indx),l_address_t(indx),l_single_burner_qnt_t(indx),tSingleBurnerPrice,
                l_double_burner_qnt_t(indx),tDoubleBurnerPrice,tBillAmount,tPayableAmount,0,tSingleBurnerConsumption*l_single_burner_qnt_t(indx)+tDoubleBurnerConsumption*l_double_burner_qnt_t(indx),to_date(iBillGenerationDate,'dd-MM-YYYY'),pay_date_holder); 
                
            Else
                tAmount:=0;
                   Begin
                         OPEN off_cursor FOR
                              'Select DAYS,BURNER_QUANTITY,BURNER_INCRASE,BURNER_RECONNECTION from BILL_NON_METERED_DTL Where Bill_Id='''||billId||'''';

                           LOOP
                             FETCH off_cursor INTO d_days,d_burner,d_increase,d_reconnection;
                             EXIT WHEN off_cursor%NOTFOUND;
                             DBMS_OUTPUT.PUT_LINE (d_days|| ' '|| d_burner|| ' '|| d_increase||''||d_reconnection);
                             
                             -- We should finalized these rules [This is not finalized yet]
                             
                          tAmount:=tAmount+d_burner*d_days*tPDC_DB*tBillRate;
                          tAmount:=tAmount+d_increase*tPDC_DB*tBillRate;
                          tAmount:=tAmount+d_reconnection*.5*tPDC_DB*tBillRate;
                          


                          End Loop;
                   End;
                   
                  
                   tBillAmount :=tAmount;
                   
                    If(l_freedom_fighter_t(indx)='Y') Then
                        tPayableAmount := tAmount-tFullBillDays*tPDC_DB*tBillRate;
                    Else
                        tPayableAmount := tAmount;
                    End If;
                    
                   
                   tBillAmount:=round(tBillAmount);
                   tPayableAmount:=round(tPayableAmount);
                 
                   Insert Into BILL_NON_METERED(BILL_ID,BILL_MONTH,BILL_YEAR,
                   CUSTOMER_ID,CUSTOMER_NAME,PROPRIETOR_NAME,CUSTOMER_CATEGORY,CUSTOMER_CATEGORY_NAME,CATEGORY_TYPE,AREA_ID,AREA_NAME,ADDRESS,SINGLE_BURNER_QNT,SINGLE_BURNER_RATE,
                   DOUBLE_BURNER_QNT,DOUBLE_BURNER_RATE,ACTUAL_BILLED_AMOUNT,ACTUAL_PAYABLE_AMOUNT,STATUS,TOTAL_CONSUMPTION,BILL_GENERATION_DATE,PREPARED_BY,DUE_DATE)
                   Values(billId,iBillMonth,iBillYear,l_customer_id_t(indx),l_full_name_t(indx),l_proprietor_t(indx),l_category_id_t(indx),l_category_name_t(indx),l_category_type_t(indx),l_area_id_t(indx),l_area_name_t(indx),l_address_t(indx),l_single_burner_qnt_t(indx),tSingleBurnerPrice,
                   l_double_burner_qnt_t(indx),tDoubleBurnerPrice,tBillAmount,tPayableAmount,0,tPDC_SB*l_single_burner_qnt_t(indx)*tFullBillDays+tPDC_DB*l_double_burner_qnt_t(indx)*tFullBillDays,to_date(iBillGenerationDate,'dd-MM-YYYY'),iUserId,To_Date('21-'||iBillMonth||'-'||iBillYear,'dd-MM-YYYY')+30);

            End If;
            

     END LOOP;
    
    dbms_output.put_line(getTimeDiff(l_start));
     
    oResponse:=1; 
    oRespMsg:= 'Successfully Processed Bill';
    EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      oResponse:=500;
      oRespMsg:='Exception Occured : '|| 'Error Code : '||SQLCODE|| ', Error Message : ' || SUBSTR(SQLERRM, 1, 400);
       Update BILLING_SEMAPHORE Set Status=0 Where Area_Id=tAreaId and ISMETERED=0;
                
       
      

END GenerateBill_NonMetered;
/


CREATE OR REPLACE PROCEDURE DB_PGCL.ApproveBill(
   iIsMetered                 IN   NUMBER,
   iBillFor                   IN   VARCHAR2,
   iBillId                    IN   VARCHAR2,
   iCustomerId                IN   VARCHAR2,
   iCategoryId                IN   VARCHAR2,  
   iAreaId                    IN   VARCHAR2,   
   iBillMonth                 IN   NUMBER,  
   iBillYear                  IN   NUMBER, 
   iUserId                    IN   VARCHAR2,  
   oResponse                  OUT  NUMBER,
   oRespMsg                   OUT  VARCHAR2
)

IS

  reading_cursor  sys_refcursor;
  where_clause varchar2(100);  
  tMonthYear varchar2(40);
  tCustomerId varchar2(20);
  tIssueDate date;
  tPayableAmount number;
  tActualBillAmount number;
  
  tAreaId  varchar2(2);
  tAreaName varchar2(50);

     
  TYPE customer_id_t IS TABLE OF BILL_METERED.CUSTOMER_ID%TYPE
     INDEX BY PLS_INTEGER;      
  TYPE issue_date_t IS TABLE OF BILL_METERED.ISSUE_DATE%TYPE
     INDEX BY PLS_INTEGER; 
  TYPE payable_amount_t IS TABLE OF BILL_METERED.PAYABLE_AMOUNT%TYPE
     INDEX BY PLS_INTEGER; 
  TYPE month_year_t IS TABLE OF VARCHAR2(40)
     INDEX BY PLS_INTEGER;  
  TYPE bill_id_t IS TABLE OF BILL_METERED.BILL_ID%TYPE
     INDEX BY PLS_INTEGER; 
                           
  l_month_year_t  month_year_t;
  l_customer_id_t   customer_id_t;
  l_issue_date_t   issue_date_t;
  l_payable_amount_t   payable_amount_t;
  l_bill_id_t   bill_id_t;
  
  tQuery varchar2(500);
  
    

BEGIN
     
     Begin
     If(iBillId>0) Then
     
       If(iIsMetered=1) Then
       
        Update BILL_METERED Set Status=1 Where Bill_Id=iBillId;
        
        Select to_char(to_date(BILL_MONTH||', '||BILL_YEAR,'MM, YYYY'),'MON, YYYY'),CUSTOMER_ID,ISSUE_DATE,PAYABLE_AMOUNT 
        Into tMonthYear,tCustomerId, tIssueDate,tPayableAmount from BILL_METERED Where Bill_Id=iBillId;
       ELSIF(iIsMetered=0) Then
          Update BILL_NON_METERED Set Status=1 Where Bill_Id=iBillId;
        
        Select to_char(to_date(BILL_MONTH||', '||BILL_YEAR,'MM, YYYY'),'MON, YYYY'),CUSTOMER_ID,BILL_GENERATION_DATE,ACTUAL_BILLED_AMOUNT 
        Into tMonthYear, tCustomerId,tIssueDate,tActualBillAmount from BILL_NON_METERED Where Bill_Id=iBillId;
       End If;
        
       Insert Into CUSTOMER_LEDGER(TRANS_ID,TRANS_DATE,PARTICULARS,DEBIT,BILL_ID,INSERTED_BY,CUSTOMER_ID,STATUS)
       Values(SQN_CL.NEXTVAL,tIssueDate,'To, Gas Sales, '||tMonthYear,tActualBillAmount,iBillId,iUserId,tCustomerId,1);
     
     
       oResponse:=1;
       oRespMsg:='Successfully Bill Approved.';
     
     Else
     
     
         If(iBillFor='individual_customer') Then 
           where_clause := ' and Customer_Id='''||iCustomerId||'''';
           Select Area into tAreaId from Customer Where customer_id= iCustomerId;
         ELSIF(iBillFor='category_wise') Then 
           where_clause := ' and customer_category='''||iCategoryId || ''' and area= '''|| iAreaId||'''';  
           tAreaId:=iAreaId;     
         ELSIF(iBillFor='area_wise') Then 
           where_clause := ' and  area_id= '''|| iAreaId||''''; 
           tAreaId:=iAreaId;      
         ELSE
           oResponse:=-1; -- invalid billFor value
           DBMS_OUTPUT.PUT_LINE ('Invalid iBillFor value');
           return;
         End If;
    
       
        If(iIsMetered=1) Then
            tQuery:='SELECT to_char(to_date('''||iBillMonth||', '||iBillYear||''',''MM, YYYY''),''MON, YYYY''),CUSTOMER_ID,ISSUE_DATE,PAYABLE_AMOUNT,BILL_ID  From BILL_METERED Where Status=0  '|| where_clause || ' And Bill_Month='''||iBillMonth||'''And Bill_Year ='''||iBillYear||'''';
        Else
            tQuery:='SELECT to_char(to_date('''||iBillMonth||', '||iBillYear||''',''MM, YYYY''),''MON, YYYY''),CUSTOMER_ID,BILL_GENERATION_DATE,PAYABLE_AMOUNT,BILL_ID  From BILL_NON_METERED Where Status=0  '|| where_clause || ' And Bill_Month='''||iBillMonth||'''And Bill_Year ='''||iBillYear||'''';
        End If;
        
        EXECUTE IMMEDIATE tQuery
        BULK COLLECT INTO l_month_year_t,l_customer_id_t,l_issue_date_t,l_payable_amount_t,l_bill_id_t;
        
        FOR indx IN 1 .. l_customer_id_t.COUNT
         LOOP
              If(iIsMetered=1) Then
                Update BILL_METERED Set Status=1 Where Bill_Id=l_bill_id_t(indx);
              ELSIF(iIsMetered=0) Then
                Update BILL_NON_METERED Set Status=1 Where Bill_Id=l_bill_id_t(indx);
              End If;
              
              Insert Into CUSTOMER_LEDGER(TRANS_ID,TRANS_DATE,PARTICULARS,DEBIT,BILL_ID,INSERTED_BY,CUSTOMER_ID,STATUS)
              Values(SQN_CL.NEXTVAL,l_issue_date_t(indx),'To, Gas Sales, '||l_month_year_t(indx),l_payable_amount_t(indx),l_bill_id_t(indx),iUserId,l_customer_id_t(indx),1);
         
         END LOOP;
         oResponse:=1;
         oRespMsg:='Successfully Approved Bill.';
         

      End If;
      
      End;
        EXCEPTION
        WHEN OTHERS THEN
        ROLLBACK;
        oResponse:=500;
        oRespMsg:='Exception Occured : '|| 'Error Code : '||SQLCODE|| ', Error Message : ' || SUBSTR(SQLERRM, 1, 400);
      
      
     
END ApproveBill;
/

--- dROP THIS VIEW

DROP VIEW VIEW_NONMETER_BILLINFO;
ALTER TABLE DB_PGCL.BILL_NON_METERED DROP COLUMN COLLECTED_AMOUNT;


CREATE SEQUENCE DB_PGCL.SQN_COLLECTION_NM
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  NOORDER;

  DROP SEQUENCE DB_PGCL.SQN_COLLECTION_M;

CREATE SEQUENCE DB_PGCL.SQN_COLLECTION_M
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  NOORDER;
  ALTER TABLE DB_PGCL.BILL_NON_METERED
RENAME COLUMN ACTUAL_SURCHARGE TO ACTUAL_SURCHARGE_AMOUNT;

ALTER TABLE DB_PGCL.BILL_NON_METERED
RENAME COLUMN COLLECTED_SURCHARGE TO COLLECTED_SURCHARGE_AMOUNT;

ALTER TABLE DB_PGCL.CUSTOMER_LEDGER
 DROP CONSTRAINT CL_UNK1;
 
 ALTER TABLE DB_PGCL.CUSTOMER_LEDGER
 DROP CONSTRAINT CL_UNK1;

ALTER TABLE DB_PGCL.CUSTOMER_LEDGER
 ADD CONSTRAINT CL_UNK1
  UNIQUE (PARTICULARS, CUSTOMER_ID, COLLECTION_ID)
  ENABLE VALIDATE;