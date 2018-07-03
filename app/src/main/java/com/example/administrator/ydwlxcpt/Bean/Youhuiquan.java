package com.example.administrator.ydwlxcpt.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/24.
 */

public class Youhuiquan implements Serializable{

    private int T_ID;
    private int T_TMID;
    private	String T_UTel;	//用户手机号
    private	int T_Ticket;	//代金券金额
    private	int T_Type;	//类型ID，默认0通用代金券
    private	int T_Money;//订单满多少使用
    private	String T_Time;	//到期时间
    private	int T_IsLock;	//是否锁定，默认0正常，1锁定
    private	String T_Remark;//说明




    public Youhuiquan() {
    }

    public int getT_ID() {
        return T_ID;
    }

    public void setT_ID(int t_ID) {
        T_ID = t_ID;
    }

    public int getT_TMID() {
        return T_TMID;
    }

    public void setT_TMID(int t_TMID) {
        T_TMID = t_TMID;
    }

    public String getT_UTel() {
        return T_UTel;
    }

    public void setT_UTel(String t_UTel) {
        T_UTel = t_UTel;
    }

    public int getT_Ticket() {
        return T_Ticket;
    }

    public void setT_Ticket(int t_Ticket) {
        T_Ticket = t_Ticket;
    }

    public int getT_Type() {
        return T_Type;
    }

    public void setT_Type(int t_Type) {
        T_Type = t_Type;
    }

    public int getT_Money() {
        return T_Money;
    }

    public void setT_Money(int t_Money) {
        T_Money = t_Money;
    }

    public String getT_Time() {
        return T_Time;
    }

    public void setT_Time(String t_Time) {
        T_Time = t_Time;
    }

    public int getT_IsLock() {
        return T_IsLock;
    }

    public void setT_IsLock(int t_IsLock) {
        T_IsLock = t_IsLock;
    }

    public String getT_Remark() {
        return T_Remark;
    }

    public void setT_Remark(String t_Remark) {
        T_Remark = t_Remark;
    }

    @Override
    public String toString() {
        return "Youhuiquan{" +
                "T_ID=" + T_ID +
                ", T_TMID=" + T_TMID +
                ", T_UTel='" + T_UTel + '\'' +
                ", T_Ticket=" + T_Ticket +
                ", T_Type=" + T_Type +
                ", T_Money=" + T_Money +
                ", T_Time='" + T_Time + '\'' +
                ", T_IsLock=" + T_IsLock +
                ", T_Remark='" + T_Remark + '\'' +
                '}';
    }
}
