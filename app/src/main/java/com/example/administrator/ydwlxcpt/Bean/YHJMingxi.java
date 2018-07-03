package com.example.administrator.ydwlxcpt.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/12.
 */

public class YHJMingxi implements Serializable {
    private int TM_ID;
    private int TM_Ticket; //	代金券金额
    private int TM_Type;    //洗车类型ID,WashCarType表W_ID列的外键，默认0通用代金券
    private int TM_Money;            //订单满多少使用
    private String TM_Time;                // 	到期时间
    private int TM_IsLock;//	 	 	 	否	0	是否锁定，默认0正常，1锁定
    private int TM_Number;// 	 	 	否	 	代金券数量
    private String TM_Remark;     //	 	是	 	备注
    public YHJMingxi() {}
    @Override
    public String toString() {
        return "YHJMingxi{" +
                "TM_ID=" + TM_ID +
                ", TM_Ticket=" + TM_Ticket +
                ", TM_Type=" + TM_Type +
                ", TM_Money=" + TM_Money +
                ", TM_Time=" + TM_Time +
                ", TM_IsLock=" + TM_IsLock +
                ", TM_Number=" + TM_Number +
                ", TM_Remark=" + TM_Remark +
                '}';
    }

    public int getTM_ID() {
        return TM_ID;
    }

    public void setTM_ID(int TM_ID) {
        this.TM_ID = TM_ID;
    }

    public int getTM_Ticket() {
        return TM_Ticket;
    }

    public void setTM_Ticket(int TM_Ticket) {
        this.TM_Ticket = TM_Ticket;
    }

    public int getTM_Type() {
        return TM_Type;
    }

    public void setTM_Type(int TM_Type) {
        this.TM_Type = TM_Type;
    }

    public int getTM_Money() {
        return TM_Money;
    }

    public void setTM_Money(int TM_Money) {
        this.TM_Money = TM_Money;
    }

    public String getTM_Time() {
        return TM_Time;
    }

    public void setTM_Time(String TM_Time) {
        this.TM_Time = TM_Time;
    }

    public int getTM_IsLock() {
        return TM_IsLock;
    }

    public void setTM_IsLock(int TM_IsLock) {
        this.TM_IsLock = TM_IsLock;
    }

    public int getTM_Number() {
        return TM_Number;
    }

    public void setTM_Number(int TM_Number) {
        this.TM_Number = TM_Number;
    }

    public String getTM_Remark() {
        return TM_Remark;
    }

    public void setTM_Remark(String TM_Remark) {
        this.TM_Remark = TM_Remark;
    }


}
