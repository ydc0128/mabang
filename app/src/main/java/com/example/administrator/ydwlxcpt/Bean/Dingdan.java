package com.example.administrator.ydwlxcpt.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/15.
 */

public class Dingdan implements Serializable{

    private int O_IDS;
    private String O_ID;            //订单号
    private String O_UTel;          //联系人手机号最多填2个手机号
    private String O_CarTel;          //车辆所属人电话
    private String O_CarName;          //车辆所属人姓名
    private String O_WPart;           //洗车类型ID，如果清洗类型多种，用|隔开
    private int O_CTID;             //车型ID，轿车5座、SUV和MPV
    private String O_UName;         //联系人姓名
    private String O_CarImage;         //车辆照片
    private String O_CarColor;         //车辆颜色
    private String O_PlateNumber;   //车牌号
    private int O_TypeID;           //订单状态ID,OrderTypes表OT_ID列的外键
    private int O_Price;            //订单金额
    private String O_Adress;        //爱车位置
    private String O_WriteAdress;        //手动输出的地址
    private float O_Lng;            //坐标经度
    private float O_Lat;            //坐标纬度
    private String O_Time;          //下单时间
    private String O_TimeEnd;       //订单完成时间
    private String O_StartTime;       //订单开始时间
    private int O_IsPhone;          //洗完车是否要给电话通知，1：打电话，0：不打电话
    private int O_EvaluateStar;     //评价等级几星
    private String O_EvaluateType;  //评价类型(服务周到、洗车专业、准时到达、风雨无阻、穿着整洁)用,隔开
    private String O_EvaluateImage;  //评价拍照
    private int O_EvaluateAnonymous;  //是否匿名，0匿名，1可显示，默认匿名
    private int O_EvaluateReward;  //打赏金额
    private int O_IsEvaluate;  //	是否已经评价，0未评价，1已评价，默认0
    private String O_Evaluate;      //评价100个字
    private int O_Money;            //实际支付金额
    private int O_TID;            //代金券ID
    private int O_Ticket;           //实际支付代金券金额
    private int O_TType;           //代金券洗车类型ID,默认0通用代金券
    private int O_TMoney;           //订单满多少使用
    private String O_TTime;           //代金券到期时间
    private int O_ISCancel;         //是否取消订单，默认为 0 ，1表示取消订单，0表示正常
    private String O_ISCancelValue; //取消订单的原因
    private int O_ISRefund;         //是否申请退款，默认 0，1表示申请退款，0表示正常
    private int O_RefundMoney;      //退款金额
    private int O_WorkerID;         //洗车工ID
    private String O_BeforePic;     //洗前照片
    private String O_AfterPic;      //洗后照片
    private String O_WorkerName;     //洗车工姓名
    private String O_WorkerImage;   //洗车工头像
    private String O_WorkerTel;   //洗车工电话
    private String O_Province;   //	洗车工省名称
    private String O_City;   //	洗车工市名称
    private String O_County;   //洗车工区县名称
    private String O_BrandName;   //汽车品牌名,该字段是CarBrand表 CB_BrandName列的外键
    private String O_BrandTypeName;   //	车型名称
    private String O_WashTime;   //洗车时间
    private int O_WashType;   //	立即服务0，预约服务1
    private String O_Remark;        //备注

    @Override
    public String toString() {
        return "Dingdan{" +
                "O_Adress='" + O_Adress + '\'' +
                ", O_IDS=" + O_IDS +
                ", O_ID='" + O_ID + '\'' +
                ", O_UTel='" + O_UTel + '\'' +
                ", O_CarTel='" + O_CarTel + '\'' +
                ", O_CarName='" + O_CarName + '\'' +
                ", O_WPart='" + O_WPart + '\'' +
                ", O_CTID=" + O_CTID +
                ", O_UName='" + O_UName + '\'' +
                ", O_CarImage='" + O_CarImage + '\'' +
                ", O_CarColor='" + O_CarColor + '\'' +
                ", O_PlateNumber='" + O_PlateNumber + '\'' +
                ", O_TypeID=" + O_TypeID +
                ", O_Price=" + O_Price +
                ", O_WriteAdress='" + O_WriteAdress + '\'' +
                ", O_Lng=" + O_Lng +
                ", O_Lat=" + O_Lat +
                ", O_Time='" + O_Time + '\'' +
                ", O_TimeEnd='" + O_TimeEnd + '\'' +
                ", O_StartTime='" + O_StartTime + '\'' +
                ", O_IsPhone=" + O_IsPhone +
                ", O_EvaluateStar=" + O_EvaluateStar +
                ", O_EvaluateType='" + O_EvaluateType + '\'' +
                ", O_EvaluateImage='" + O_EvaluateImage + '\'' +
                ", O_EvaluateAnonymous=" + O_EvaluateAnonymous +
                ", O_EvaluateReward=" + O_EvaluateReward +
                ", O_IsEvaluate=" + O_IsEvaluate +
                ", O_Evaluate='" + O_Evaluate + '\'' +
                ", O_Money=" + O_Money +
                ", O_TID=" + O_TID +
                ", O_Ticket=" + O_Ticket +
                ", O_TType=" + O_TType +
                ", O_TMoney=" + O_TMoney +
                ", O_TTime='" + O_TTime + '\'' +
                ", O_ISCancel=" + O_ISCancel +
                ", O_ISCancelValue='" + O_ISCancelValue + '\'' +
                ", O_ISRefund=" + O_ISRefund +
                ", O_RefundMoney=" + O_RefundMoney +
                ", O_WorkerID=" + O_WorkerID +
                ", O_BeforePic='" + O_BeforePic + '\'' +
                ", O_AfterPic='" + O_AfterPic + '\'' +
                ", O_WorkerName='" + O_WorkerName + '\'' +
                ", O_WorkerImage='" + O_WorkerImage + '\'' +
                ", O_WorkerTel='" + O_WorkerTel + '\'' +
                ", O_Province='" + O_Province + '\'' +
                ", O_City='" + O_City + '\'' +
                ", O_County='" + O_County + '\'' +
                ", O_BrandName='" + O_BrandName + '\'' +
                ", O_BrandTypeName='" + O_BrandTypeName + '\'' +
                ", O_WashTime='" + O_WashTime + '\'' +
                ", O_WashType=" + O_WashType +
                ", O_Remark='" + O_Remark + '\'' +
                '}';
    }

    public String getO_StartTime() {
        return O_StartTime;
    }

    public void setO_StartTime(String o_StartTime) {
        O_StartTime = o_StartTime;
    }

    public Dingdan() {
    }



    public String getO_Adress() {
        return O_Adress;
    }

    public void setO_Adress(String o_Adress) {
        O_Adress = o_Adress;
    }

    public String getO_AfterPic() {
        return O_AfterPic;
    }

    public void setO_AfterPic(String o_AfterPic) {
        O_AfterPic = o_AfterPic;
    }

    public String getO_BeforePic() {
        return O_BeforePic;
    }

    public void setO_BeforePic(String o_BeforePic) {
        O_BeforePic = o_BeforePic;
    }

    public String getO_BrandName() {
        return O_BrandName;
    }

    public void setO_BrandName(String o_BrandName) {
        O_BrandName = o_BrandName;
    }

    public String getO_BrandTypeName() {
        return O_BrandTypeName;
    }

    public void setO_BrandTypeName(String o_BrandTypeName) {
        O_BrandTypeName = o_BrandTypeName;
    }

    public String getO_CarColor() {
        return O_CarColor;
    }

    public void setO_CarColor(String o_CarColor) {
        O_CarColor = o_CarColor;
    }

    public String getO_CarImage() {
        return O_CarImage;
    }

    public void setO_CarImage(String o_CarImage) {
        O_CarImage = o_CarImage;
    }

    public String getO_CarName() {
        return O_CarName;
    }

    public void setO_CarName(String o_CarName) {
        O_CarName = o_CarName;
    }

    public String getO_CarTel() {
        return O_CarTel;
    }

    public void setO_CarTel(String o_CarTel) {
        O_CarTel = o_CarTel;
    }

    public String getO_City() {
        return O_City;
    }

    public void setO_City(String o_City) {
        O_City = o_City;
    }

    public String getO_County() {
        return O_County;
    }

    public void setO_County(String o_County) {
        O_County = o_County;
    }

    public int getO_CTID() {
        return O_CTID;
    }

    public void setO_CTID(int o_CTID) {
        O_CTID = o_CTID;
    }

    public String getO_Evaluate() {
        return O_Evaluate;
    }

    public void setO_Evaluate(String o_Evaluate) {
        O_Evaluate = o_Evaluate;
    }

    public int getO_EvaluateAnonymous() {
        return O_EvaluateAnonymous;
    }

    public void setO_EvaluateAnonymous(int o_EvaluateAnonymous) {
        O_EvaluateAnonymous = o_EvaluateAnonymous;
    }

    public String getO_EvaluateImage() {
        return O_EvaluateImage;
    }

    public void setO_EvaluateImage(String o_EvaluateImage) {
        O_EvaluateImage = o_EvaluateImage;
    }

    public int getO_EvaluateReward() {
        return O_EvaluateReward;
    }

    public void setO_EvaluateReward(int o_EvaluateReward) {
        O_EvaluateReward = o_EvaluateReward;
    }

    public int getO_EvaluateStar() {
        return O_EvaluateStar;
    }

    public void setO_EvaluateStar(int o_EvaluateStar) {
        O_EvaluateStar = o_EvaluateStar;
    }

    public String getO_EvaluateType() {
        return O_EvaluateType;
    }

    public void setO_EvaluateType(String o_EvaluateType) {
        O_EvaluateType = o_EvaluateType;
    }

    public String getO_ID() {
        return O_ID;
    }

    public void setO_ID(String o_ID) {
        O_ID = o_ID;
    }

    public int getO_IDS() {
        return O_IDS;
    }

    public void setO_IDS(int o_IDS) {
        O_IDS = o_IDS;
    }

    public int getO_ISCancel() {
        return O_ISCancel;
    }

    public void setO_ISCancel(int o_ISCancel) {
        O_ISCancel = o_ISCancel;
    }

    public String getO_ISCancelValue() {
        return O_ISCancelValue;
    }

    public void setO_ISCancelValue(String o_ISCancelValue) {
        O_ISCancelValue = o_ISCancelValue;
    }

    public int getO_IsEvaluate() {
        return O_IsEvaluate;
    }

    public void setO_IsEvaluate(int o_IsEvaluate) {
        O_IsEvaluate = o_IsEvaluate;
    }

    public int getO_IsPhone() {
        return O_IsPhone;
    }

    public void setO_IsPhone(int o_IsPhone) {
        O_IsPhone = o_IsPhone;
    }

    public int getO_ISRefund() {
        return O_ISRefund;
    }

    public void setO_ISRefund(int o_ISRefund) {
        O_ISRefund = o_ISRefund;
    }

    public float getO_Lat() {
        return O_Lat;
    }

    public void setO_Lat(float o_Lat) {
        O_Lat = o_Lat;
    }

    public float getO_Lng() {
        return O_Lng;
    }

    public void setO_Lng(float o_Lng) {
        O_Lng = o_Lng;
    }

    public int getO_Money() {
        return O_Money;
    }

    public void setO_Money(int o_Money) {
        O_Money = o_Money;
    }

    public String getO_PlateNumber() {
        return O_PlateNumber;
    }

    public void setO_PlateNumber(String o_PlateNumber) {
        O_PlateNumber = o_PlateNumber;
    }

    public int getO_Price() {
        return O_Price;
    }

    public void setO_Price(int o_Price) {
        O_Price = o_Price;
    }

    public String getO_Province() {
        return O_Province;
    }

    public void setO_Province(String o_Province) {
        O_Province = o_Province;
    }

    public int getO_RefundMoney() {
        return O_RefundMoney;
    }

    public void setO_RefundMoney(int o_RefundMoney) {
        O_RefundMoney = o_RefundMoney;
    }

    public String getO_Remark() {
        return O_Remark;
    }

    public void setO_Remark(String o_Remark) {
        O_Remark = o_Remark;
    }

    public int getO_Ticket() {
        return O_Ticket;
    }

    public void setO_Ticket(int o_Ticket) {
        O_Ticket = o_Ticket;
    }

    public int getO_TID() {
        return O_TID;
    }

    public void setO_TID(int o_TID) {
        O_TID = o_TID;
    }

    public String getO_Time() {
        return O_Time;
    }

    public void setO_Time(String o_Time) {
        O_Time = o_Time;
    }

    public String getO_TimeEnd() {
        return O_TimeEnd;
    }

    public void setO_TimeEnd(String o_TimeEnd) {
        O_TimeEnd = o_TimeEnd;
    }

    public int getO_TMoney() {
        return O_TMoney;
    }

    public void setO_TMoney(int o_TMoney) {
        O_TMoney = o_TMoney;
    }

    public String getO_TTime() {
        return O_TTime;
    }

    public void setO_TTime(String o_TTime) {
        O_TTime = o_TTime;
    }

    public int getO_TType() {
        return O_TType;
    }

    public void setO_TType(int o_TType) {
        O_TType = o_TType;
    }

    public int getO_TypeID() {
        return O_TypeID;
    }

    public void setO_TypeID(int o_TypeID) {
        O_TypeID = o_TypeID;
    }

    public String getO_UName() {
        return O_UName;
    }

    public void setO_UName(String o_UName) {
        O_UName = o_UName;
    }

    public String getO_UTel() {
        return O_UTel;
    }

    public void setO_UTel(String o_UTel) {
        O_UTel = o_UTel;
    }

    public String getO_WashTime() {
        return O_WashTime;
    }

    public void setO_WashTime(String o_WashTime) {
        O_WashTime = o_WashTime;
    }

    public int getO_WashType() {
        return O_WashType;
    }

    public void setO_WashType(int o_WashType) {
        O_WashType = o_WashType;
    }

    public int getO_WorkerID() {
        return O_WorkerID;
    }

    public void setO_WorkerID(int o_WorkerID) {
        O_WorkerID = o_WorkerID;
    }

    public String getO_WorkerImage() {
        return O_WorkerImage;
    }

    public void setO_WorkerImage(String o_WorkerImage) {
        O_WorkerImage = o_WorkerImage;
    }

    public String getO_WorkerName() {
        return O_WorkerName;
    }

    public void setO_WorkerName(String o_WorkerName) {
        O_WorkerName = o_WorkerName;
    }

    public String getO_WorkerTel() {
        return O_WorkerTel;
    }

    public void setO_WorkerTel(String o_WorkerTel) {
        O_WorkerTel = o_WorkerTel;
    }

    public String getO_WPart() {
        return O_WPart;
    }

    public void setO_WPart(String o_WPart) {
        O_WPart = o_WPart;
    }

    public String getO_WriteAdress() {
        return O_WriteAdress;
    }

    public void setO_WriteAdress(String o_WriteAdress) {
        O_WriteAdress = o_WriteAdress;
    }
}
