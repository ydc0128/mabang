package com.example.administrator.ydwlxcpt.Bean;
/**
 * Created by Administrator on 2017/8/22.
 */

public class EntityWashCarTypes {

    private int W_ID;
    private String W_Name;  //洗车类型
    private String W_Price; //洗车价格
    private int W_CTID; //汽车类型ID，CarType表CT_ID的外键
    private int W_Side; //是否需要车主在车旁，1在，0不在
    private String W_Title; //提示信息
    private int W_DurationMin; //洗车时长最短时间分钟int
    private int W_DurationMax; //洗车时长最大时间分钟int
    private String W_Remark; //备注

    public EntityWashCarTypes() {
    }

    @Override
    public String toString() {
        return "EntityWashCarTypes{" +
                "W_ID=" + W_ID +
                ", W_Name='" + W_Name + '\'' +
                ", W_Price='" + W_Price + '\'' +
                ", W_CTID=" + W_CTID +
                ", W_Side=" + W_Side +
                ", W_Title='" + W_Title + '\'' +
                ", W_DurationMin=" + W_DurationMin +
                ", W_DurationMax=" + W_DurationMax +
                ", W_Remark='" + W_Remark + '\'' +
                '}';
    }

    public int getW_ID() {
        return W_ID;
    }

    public void setW_ID(int w_ID) {
        W_ID = w_ID;
    }

    public String getW_Name() {
        return W_Name;
    }

    public void setW_Name(String w_Name) {
        W_Name = w_Name;
    }

    public String getW_Price() {
        return W_Price;
    }

    public void setW_Price(String w_Price) {
        W_Price = w_Price;
    }

    public int getW_CTID() {
        return W_CTID;
    }

    public void setW_CTID(int w_CTID) {
        W_CTID = w_CTID;
    }

    public int getW_Side() {
        return W_Side;
    }

    public void setW_Side(int w_Side) {
        W_Side = w_Side;
    }

    public String getW_Title() {
        return W_Title;
    }

    public void setW_Title(String w_Title) {
        W_Title = w_Title;
    }

    public int getW_DurationMin() {
        return W_DurationMin;
    }

    public void setW_DurationMin(int w_DurationMin) {
        W_DurationMin = w_DurationMin;
    }

    public int getW_DurationMax() {
        return W_DurationMax;
    }

    public void setW_DurationMax(int w_DurationMax) {
        W_DurationMax = w_DurationMax;
    }

    public String getW_Remark() {
        return W_Remark;
    }

    public void setW_Remark(String w_Remark) {
        W_Remark = w_Remark;
    }
}
