package com.example.administrator.ydwlxcpt.Bean;

/**
 * Created by Administrator on 2017/8/9.
 */

public class User {
    private int U_ID;        ///
    private String U_Name;        //	 	姓名
    private String U_Image;        //	 	头像
    private String U_Sex;        //	 	性别
    private String U_Pwd;        //密码
    private String U_Tel;        //电话
    private String U_CreatTime;        //注册时间
    private String U_UpdateTime;        //修改时间
    private int U_IsLock;        //锁定账户,0表示正常，1锁定
    private int U_Isonline;    //是否在线,1在线，0下线，默认0
    private int U_OnlineTime;        //在线时长
    private int U_OnlineAllTime;        //在线总时长
    private int U_Money;    //余额元
    private int U_Ticket;        //否	0	券元
    private String U_Car;        //已洗过的车ID和次数，多个车用 | 分隔
    private String U_IdentityType;        //证件类型（1居民身份证、2护照、3军官证、4驾驶证、5港澳回乡证台胞证）
    private String U_IdentityCard;        //证件号码
    private int U_Grade;    //会员等级（普通会员、白金会员）
    private int U_Integral;        //会员积分
    private String U_IMEI;        //用户保存验证码
    private int U_LSID;    //临时列，当注册时候，写入一个临时ID
    private int U_IdentityID;    //保存验证码
    private String U_Home;        //家地址
    private String U_HomeWrite;        //	家地址手写
    private String U_Office;        //公司地址
    private String U_OfficeWrite;        //公司地址手写
    private float U_HomeLng;        //	家地址经度
    private float U_HomeLat;        //	家地址纬度
    private float U_OfficeLng;        //公司地址经度
    private float U_OfficeLat;        //公司地址纬度
    private String U_Remark;        //备注
    private String U_RegistrationID;

    public User() {
    }

    public int getU_ID() {
        return U_ID;
    }

    public void setU_ID(int u_ID) {
        U_ID = u_ID;
    }

    public String getU_Name() {
        return U_Name;
    }

    public void setU_Name(String u_Name) {
        U_Name = u_Name;
    }

    public String getU_Image() {
        return U_Image;
    }

    public void setU_Image(String u_Image) {
        U_Image = u_Image;
    }

    public String getU_Sex() {
        return U_Sex;
    }

    public void setU_Sex(String u_Sex) {
        U_Sex = u_Sex;
    }

    public String getU_Pwd() {
        return U_Pwd;
    }

    public void setU_Pwd(String u_Pwd) {
        U_Pwd = u_Pwd;
    }

    public String getU_Tel() {
        return U_Tel;
    }

    public void setU_Tel(String u_Tel) {
        U_Tel = u_Tel;
    }

    public String getU_CreatTime() {
        return U_CreatTime;
    }

    public void setU_CreatTime(String u_CreatTime) {
        U_CreatTime = u_CreatTime;
    }

    public String getU_UpdateTime() {
        return U_UpdateTime;
    }

    public void setU_UpdateTime(String u_UpdateTime) {
        U_UpdateTime = u_UpdateTime;
    }

    public int getU_IsLock() {
        return U_IsLock;
    }

    public void setU_IsLock(int u_IsLock) {
        U_IsLock = u_IsLock;
    }

    public int getU_Isonline() {
        return U_Isonline;
    }

    public void setU_Isonline(int u_Isonline) {
        U_Isonline = u_Isonline;
    }

    public int getU_OnlineTime() {
        return U_OnlineTime;
    }

    public void setU_OnlineTime(int u_OnlineTime) {
        U_OnlineTime = u_OnlineTime;
    }

    public int getU_OnlineAllTime() {
        return U_OnlineAllTime;
    }

    public void setU_OnlineAllTime(int u_OnlineAllTime) {
        U_OnlineAllTime = u_OnlineAllTime;
    }

    public int getU_Money() {
        return U_Money;
    }

    public void setU_Money(int u_Money) {
        U_Money = u_Money;
    }

    public int getU_Ticket() {
        return U_Ticket;
    }

    public void setU_Ticket(int u_Ticket) {
        U_Ticket = u_Ticket;
    }

    public String getU_Car() {
        return U_Car;
    }

    public void setU_Car(String u_Car) {
        U_Car = u_Car;
    }

    public String getU_IdentityType() {
        return U_IdentityType;
    }

    public void setU_IdentityType(String u_IdentityType) {
        U_IdentityType = u_IdentityType;
    }

    public String getU_IdentityCard() {
        return U_IdentityCard;
    }

    public void setU_IdentityCard(String u_IdentityCard) {
        U_IdentityCard = u_IdentityCard;
    }

    public int getU_Grade() {
        return U_Grade;
    }

    public void setU_Grade(int u_Grade) {
        U_Grade = u_Grade;
    }

    public int getU_Integral() {
        return U_Integral;
    }

    public void setU_Integral(int u_Integral) {
        U_Integral = u_Integral;
    }

    public String getU_IMEI() {
        return U_IMEI;
    }

    public void setU_IMEI(String u_IMEI) {
        U_IMEI = u_IMEI;
    }

    public int getU_LSID() {
        return U_LSID;
    }

    public void setU_LSID(int u_LSID) {
        U_LSID = u_LSID;
    }

    public int getU_IdentityID() {
        return U_IdentityID;
    }

    public void setU_IdentityID(int u_IdentityID) {
        U_IdentityID = u_IdentityID;
    }

    public String getU_Home() {
        return U_Home;
    }

    public void setU_Home(String u_Home) {
        U_Home = u_Home;
    }

    public String getU_HomeWrite() {
        return U_HomeWrite;
    }

    public void setU_HomeWrite(String u_HomeWrite) {
        U_HomeWrite = u_HomeWrite;
    }

    public String getU_Office() {
        return U_Office;
    }

    public void setU_Office(String u_Office) {
        U_Office = u_Office;
    }

    public String getU_OfficeWrite() {
        return U_OfficeWrite;
    }

    public void setU_OfficeWrite(String u_OfficeWrite) {
        U_OfficeWrite = u_OfficeWrite;
    }

    public float getU_HomeLng() {
        return U_HomeLng;
    }

    public void setU_HomeLng(float u_HomeLng) {
        U_HomeLng = u_HomeLng;
    }

    public float getU_HomeLat() {
        return U_HomeLat;
    }

    public void setU_HomeLat(float u_HomeLat) {
        U_HomeLat = u_HomeLat;
    }

    public float getU_OfficeLng() {
        return U_OfficeLng;
    }

    public void setU_OfficeLng(float u_OfficeLng) {
        U_OfficeLng = u_OfficeLng;
    }

    public float getU_OfficeLat() {
        return U_OfficeLat;
    }

    public void setU_OfficeLat(float u_OfficeLat) {
        U_OfficeLat = u_OfficeLat;
    }

    public String getU_Remark() {
        return U_Remark;
    }

    public void setU_Remark(String u_Remark) {
        U_Remark = u_Remark;
    }

    public String getU_RegistrationID() {
        return U_RegistrationID;
    }

    public void setU_RegistrationID(String u_RegistrationID) {
        U_RegistrationID = u_RegistrationID;
    }

    @Override
    public String toString() {
        return "User{" +
                "U_ID=" + U_ID +
                ", U_Name='" + U_Name + '\'' +
                ", U_Image='" + U_Image + '\'' +
                ", U_Sex='" + U_Sex + '\'' +
                ", U_Pwd='" + U_Pwd + '\'' +
                ", U_Tel='" + U_Tel + '\'' +
                ", U_CreatTime='" + U_CreatTime + '\'' +
                ", U_UpdateTime='" + U_UpdateTime + '\'' +
                ", U_IsLock=" + U_IsLock +
                ", U_Isonline=" + U_Isonline +
                ", U_OnlineTime=" + U_OnlineTime +
                ", U_OnlineAllTime=" + U_OnlineAllTime +
                ", U_Money=" + U_Money +
                ", U_Ticket=" + U_Ticket +
                ", U_Car='" + U_Car + '\'' +
                ", U_IdentityType='" + U_IdentityType + '\'' +
                ", U_IdentityCard='" + U_IdentityCard + '\'' +
                ", U_Grade=" + U_Grade +
                ", U_Integral=" + U_Integral +
                ", U_IMEI='" + U_IMEI + '\'' +
                ", U_LSID=" + U_LSID +
                ", U_IdentityID=" + U_IdentityID +
                ", U_Home='" + U_Home + '\'' +
                ", U_HomeWrite='" + U_HomeWrite + '\'' +
                ", U_Office='" + U_Office + '\'' +
                ", U_OfficeWrite='" + U_OfficeWrite + '\'' +
                ", U_HomeLng=" + U_HomeLng +
                ", U_HomeLat=" + U_HomeLat +
                ", U_OfficeLng=" + U_OfficeLng +
                ", U_OfficeLat=" + U_OfficeLat +
                ", U_Remark='" + U_Remark + '\'' +
                ", U_RegistrationID='" + U_RegistrationID + '\'' +
                '}';
    }
}