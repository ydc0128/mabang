package com.example.administrator.ydwlxcpt.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/21.
 */

public class Car implements Serializable{

    private int C_ID;
    private	String C_Image;	//爱车照片
    private	String C_PlateNumber;	//车牌号
    private	String C_CarTypeName;	//车型名称，洗车时手工输入，和保险的车型分开
    private	int C_CarTypeID;	//车型ID,该列是CarBrandType表CBT_ID列的外键
    private	String C_BrandName;	//品牌
    private	String C_BrandTypeName;	//型号
    private	String C_Color;	//车辆颜色
    private	String C_MoterNumber;	//发动机号
    private	String C_VIN;	//车架号
    private	String C_CarTime;	//车辆购买日期
    private	int C_IdentityType;	//证件类型（1居民身份证、2护照、3军官证、4驾驶证、5港澳回乡证台胞证）
    private String C_IdentityCard;	//证件号码
    private	String C_CarTel;	//车辆所属人电话
    private	String C_CarName;	//车辆所属人姓名
    private	String C_UTel;	//车辆所属用户的手机号
    private	int C_CTID;	//汽车类型ID，CarType表的CT_ID列的外键，3表示五坐轿车，4表示其他车型
    private	String C_Remark;	//备注

    public Car() {
    }

    @Override
    public String toString() {
        return "Car{" +
                "C_BrandName='" + C_BrandName + '\'' +
                ", C_ID=" + C_ID +
                ", C_Image='" + C_Image + '\'' +
                ", C_PlateNumber='" + C_PlateNumber + '\'' +
                ", C_CarTypeName='" + C_CarTypeName + '\'' +
                ", C_CarTypeID=" + C_CarTypeID +
                ", C_BrandTypeName='" + C_BrandTypeName + '\'' +
                ", C_Color='" + C_Color + '\'' +
                ", C_MoterNumber='" + C_MoterNumber + '\'' +
                ", C_VIN='" + C_VIN + '\'' +
                ", C_CarTime='" + C_CarTime + '\'' +
                ", C_IdentityType=" + C_IdentityType +
                ", C_IdentityCard='" + C_IdentityCard + '\'' +
                ", C_CarTel='" + C_CarTel + '\'' +
                ", C_CarName='" + C_CarName + '\'' +
                ", C_UTel='" + C_UTel + '\'' +
                ", C_CTID=" + C_CTID +
                ", C_Remark='" + C_Remark + '\'' +
                '}';
    }

    public String getC_BrandName() {
        return C_BrandName;
    }

    public void setC_BrandName(String c_BrandName) {
        C_BrandName = c_BrandName;
    }

    public String getC_BrandTypeName() {
        return C_BrandTypeName;
    }

    public void setC_BrandTypeName(String c_BrandTypeName) {
        C_BrandTypeName = c_BrandTypeName;
    }

    public String getC_CarName() {
        return C_CarName;
    }

    public void setC_CarName(String c_CarName) {
        C_CarName = c_CarName;
    }

    public String getC_CarTel() {
        return C_CarTel;
    }

    public void setC_CarTel(String c_CarTel) {
        C_CarTel = c_CarTel;
    }

    public String getC_CarTime() {
        return C_CarTime;
    }

    public void setC_CarTime(String c_CarTime) {
        C_CarTime = c_CarTime;
    }

    public int getC_CarTypeID() {
        return C_CarTypeID;
    }

    public void setC_CarTypeID(int c_CarTypeID) {
        C_CarTypeID = c_CarTypeID;
    }

    public String getC_CarTypeName() {
        return C_CarTypeName;
    }

    public void setC_CarTypeName(String c_CarTypeName) {
        C_CarTypeName = c_CarTypeName;
    }

    public String getC_Color() {
        return C_Color;
    }

    public void setC_Color(String c_Color) {
        C_Color = c_Color;
    }

    public int getC_CTID() {
        return C_CTID;
    }

    public void setC_CTID(int c_CTID) {
        C_CTID = c_CTID;
    }

    public int getC_ID() {
        return C_ID;
    }

    public void setC_ID(int c_ID) {
        C_ID = c_ID;
    }

    public String getC_IdentityCard() {
        return C_IdentityCard;
    }

    public void setC_IdentityCard(String c_IdentityCard) {
        C_IdentityCard = c_IdentityCard;
    }

    public int getC_IdentityType() {
        return C_IdentityType;
    }

    public void setC_IdentityType(int c_IdentityType) {
        C_IdentityType = c_IdentityType;
    }

    public String getC_Image() {
        return C_Image;
    }

    public void setC_Image(String c_Image) {
        C_Image = c_Image;
    }

    public String getC_MoterNumber() {
        return C_MoterNumber;
    }

    public void setC_MoterNumber(String c_MoterNumber) {
        C_MoterNumber = c_MoterNumber;
    }

    public String getC_PlateNumber() {
        return C_PlateNumber;
    }

    public void setC_PlateNumber(String c_PlateNumber) {
        C_PlateNumber = c_PlateNumber;
    }

    public String getC_Remark() {
        return C_Remark;
    }

    public void setC_Remark(String c_Remark) {
        C_Remark = c_Remark;
    }

    public String getC_UTel() {
        return C_UTel;
    }

    public void setC_UTel(String c_UTel) {
        C_UTel = c_UTel;
    }

    public String getC_VIN() {
        return C_VIN;
    }

    public void setC_VIN(String c_VIN) {
        C_VIN = c_VIN;
    }
}
