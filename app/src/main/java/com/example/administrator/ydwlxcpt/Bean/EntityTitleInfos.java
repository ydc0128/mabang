package com.example.administrator.ydwlxcpt.Bean;

/**
 * Created by Administrator on 2017/8/22.
 */

public class EntityTitleInfos {

    private int T_ID;
    private int T_Type;     //类型，1表示平台信息，2表示Order表的评价类型O_EvaluateType
    private String T_Value; //提示信息正文
    private String T_Name;  //名称
    private String T_Remark;//备注

    @Override
    public String toString() {
        return "EntityTitleInfos{" +
                "T_ID=" + T_ID +
                ", T_Type=" + T_Type +
                ", T_Value='" + T_Value + '\'' +
                ", T_Name='" + T_Name + '\'' +
                ", T_Remark='" + T_Remark + '\'' +
                '}';
    }

    public EntityTitleInfos() {
    }

    public int getT_ID() {
        return T_ID;
    }

    public void setT_ID(int t_ID) {
        T_ID = t_ID;
    }

    public int getT_Type() {
        return T_Type;
    }

    public void setT_Type(int t_Type) {
        T_Type = t_Type;
    }

    public String getT_Value() {
        return T_Value;
    }

    public void setT_Value(String t_Value) {
        T_Value = t_Value;
    }

    public String getT_Name() {
        return T_Name;
    }

    public void setT_Name(String t_Name) {
        T_Name = t_Name;
    }

    public String getT_Remark() {
        return T_Remark;
    }

    public void setT_Remark(String t_Remark) {
        T_Remark = t_Remark;
    }
}
