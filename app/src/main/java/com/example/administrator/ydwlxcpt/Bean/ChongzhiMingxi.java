package com.example.administrator.ydwlxcpt.Bean;

/**
 * Created by Administrator on 2017/8/29.
 */

public class ChongzhiMingxi {
    private int	P_ID;
    private	int	P_TypeID;	//支付类型表ID（套餐类型）(0为不享受套餐-1为平台给洗车工或用户支付)
    private	String P_PayUTel;	//支付方手机号
    private	int	P_PayUserType;	//支付方身份，1车主，2洗车工，3平台
    private	double P_PayPrice;	//支付金额
    private	double P_PayPriceAdd;	//支付附加金额
    private	double P_PayMoney;	//支付方余额
    private	String P_TakeUTel;	//收款人手机号
    private	int P_TakeUserType;	//收款方身份，1车主，2洗车工，3平台
    private	double P_TakePrice;	//收款方收到金额
    private	double P_TakePriceAdd;	//收款附加金额
    private	double P_TakeMoney;	//收款方余额
    private	String P_Time;	//交易时间
    private	int P_Type;	//支付类型，1支付宝，2微信
    private	String P_Remark;	//备注

    @Override
    public String toString() {
        return "ChongzhiMingxi{" +
                "P_ID=" + P_ID +
                ", P_TypeID=" + P_TypeID +
                ", P_PayUTel='" + P_PayUTel + '\'' +
                ", P_PayUserType=" + P_PayUserType +
                ", P_PayPrice=" + P_PayPrice +
                ", P_PayPriceAdd=" + P_PayPriceAdd +
                ", P_PayMoney=" + P_PayMoney +
                ", P_TakeUTel='" + P_TakeUTel + '\'' +
                ", P_TakeUserType=" + P_TakeUserType +
                ", P_TakePrice=" + P_TakePrice +
                ", P_TakePriceAdd=" + P_TakePriceAdd +
                ", P_TakeMoney=" + P_TakeMoney +
                ", P_Time='" + P_Time + '\'' +
                ", P_Type=" + P_Type +
                ", P_Remark='" + P_Remark + '\'' +
                '}';
    }

    public ChongzhiMingxi() {
    }

    public int getP_TypeID() {

        return P_TypeID;
    }

    public void setP_TypeID(int p_TypeID) {
        P_TypeID = p_TypeID;
    }

    public int getP_ID() {
        return P_ID;
    }

    public void setP_ID(int p_ID) {
        P_ID = p_ID;
    }

    public double getP_PayMoney() {
        return P_PayMoney;
    }

    public void setP_PayMoney(double p_PayMoney) {
        P_PayMoney = p_PayMoney;
    }

    public double getP_PayPrice() {
        return P_PayPrice;
    }

    public void setP_PayPrice(double p_PayPrice) {
        P_PayPrice = p_PayPrice;
    }

    public double getP_PayPriceAdd() {
        return P_PayPriceAdd;
    }

    public void setP_PayPriceAdd(double p_PayPriceAdd) {
        P_PayPriceAdd = p_PayPriceAdd;
    }

    public int getP_PayUserType() {
        return P_PayUserType;
    }

    public void setP_PayUserType(int p_PayUserType) {
        P_PayUserType = p_PayUserType;
    }

    public String getP_PayUTel() {
        return P_PayUTel;
    }

    public void setP_PayUTel(String p_PayUTel) {
        P_PayUTel = p_PayUTel;
    }

    public String getP_Remark() {
        return P_Remark;
    }

    public void setP_Remark(String p_Remark) {
        P_Remark = p_Remark;
    }

    public double getP_TakeMoney() {
        return P_TakeMoney;
    }

    public void setP_TakeMoney(double p_TakeMoney) {
        P_TakeMoney = p_TakeMoney;
    }

    public double getP_TakePrice() {
        return P_TakePrice;
    }

    public void setP_TakePrice(double p_TakePrice) {
        P_TakePrice = p_TakePrice;
    }

    public double getP_TakePriceAdd() {
        return P_TakePriceAdd;
    }

    public void setP_TakePriceAdd(double p_TakePriceAdd) {
        P_TakePriceAdd = p_TakePriceAdd;
    }

    public int getP_TakeUserType() {
        return P_TakeUserType;
    }

    public void setP_TakeUserType(int p_TakeUserType) {
        P_TakeUserType = p_TakeUserType;
    }

    public String getP_TakeUTel() {
        return P_TakeUTel;
    }

    public void setP_TakeUTel(String p_TakeUTel) {
        P_TakeUTel = p_TakeUTel;
    }

    public String getP_Time() {
        return P_Time;
    }

    public void setP_Time(String p_Time) {
        P_Time = p_Time;
    }

    public int getP_Type() {
        return P_Type;
    }

    public void setP_Type(int p_Type) {
        P_Type = p_Type;
    }
}
