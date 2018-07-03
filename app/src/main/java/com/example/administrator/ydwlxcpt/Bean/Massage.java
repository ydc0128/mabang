package com.example.administrator.ydwlxcpt.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/22.
 */

public class Massage implements Serializable {
    private int M_ID;
    private int M_PartID;
    private String M_Title;        //标题
    private String M_Info;//内容
    private int M_Islock;//正常显示，1锁定（过期消息锁定不再显示）
    private String M_Remark;
    private int MP_ID;
    private String MP_Name;
    private String MP_Remark;
    public  Massage(){}

    @Override
    public String toString() {
        return "Massage{" +
                "M_ID=" + M_ID +
                ", M_PartID=" + M_PartID +
                ", M_Title='" + M_Title + '\'' +
                ", M_Info='" + M_Info + '\'' +
                ", M_Islock=" + M_Islock +
                ", M_Remark='" + M_Remark + '\'' +
                ", MP_ID=" + MP_ID +
                ", MP_Name='" + MP_Name + '\'' +
                ", MP_Remark='" + MP_Remark + '\'' +
                '}';
    }

    public int getM_ID() {
        return M_ID;
    }

    public void setM_ID(int m_ID) {
        M_ID = m_ID;
    }

    public int getM_PartID() {
        return M_PartID;
    }

    public void setM_PartID(int m_PartID) {
        M_PartID = m_PartID;
    }

    public String getM_Title() {
        return M_Title;
    }

    public void setM_Title(String m_Title) {
        M_Title = m_Title;
    }

    public String getM_Info() {
        return M_Info;
    }

    public void setM_Info(String m_Info) {
        M_Info = m_Info;
    }

    public int getM_Islock() {
        return M_Islock;
    }

    public void setM_Islock(int m_Islock) {
        M_Islock = m_Islock;
    }

    public String getM_Remark() {
        return M_Remark;
    }

    public void setM_Remark(String m_Remark) {
        M_Remark = m_Remark;
    }

    public int getMP_ID() {
        return MP_ID;
    }

    public void setMP_ID(int MP_ID) {
        this.MP_ID = MP_ID;
    }

    public String getMP_Name() {
        return MP_Name;
    }

    public void setMP_Name(String MP_Name) {
        this.MP_Name = MP_Name;
    }

    public String getMP_Remark() {
        return MP_Remark;
    }

    public void setMP_Remark(String MP_Remark) {
        this.MP_Remark = MP_Remark;
    }
}
