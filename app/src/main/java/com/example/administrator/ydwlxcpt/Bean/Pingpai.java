package com.example.administrator.ydwlxcpt.Bean;

import com.example.administrator.ydwlxcpt.View.SideBar;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/20.
 */

public class Pingpai implements Serializable,SideBar.JieKou {

    private int CB_ID;              //id
    private String CB_FirstLetter;  //首字母
    private String CB_BrandName;    //名称
    private String CB_Image;        //图片
    private String CB_Remark;       //备注

    public Pingpai() {
    }

    @Override
    public String toString() {
        return "Pingpai{" +
                "CB_BrandName='" + CB_BrandName + '\'' +
                ", CB_ID=" + CB_ID +
                ", CB_FirstLetter='" + CB_FirstLetter + '\'' +
                ", CB_Image='" + CB_Image + '\'' +
                ", CB_Remark='" + CB_Remark + '\'' +
                '}';
    }

    public String getCB_BrandName() {
        return CB_BrandName;
    }

    public void setCB_BrandName(String CB_BrandName) {
        this.CB_BrandName = CB_BrandName;
    }

    public String getCB_FirstLetter() {
        return CB_FirstLetter;
    }

    public void setCB_FirstLetter(String CB_FirstLetter) {
        this.CB_FirstLetter = CB_FirstLetter;
    }

    public int getCB_ID() {
        return CB_ID;
    }

    public void setCB_ID(int CB_ID) {
        this.CB_ID = CB_ID;
    }

    public String getCB_Image() {
        return CB_Image;
    }

    public void setCB_Image(String CB_Image) {
        this.CB_Image = CB_Image;
    }

    public String getCB_Remark() {
        return CB_Remark;
    }

    public void setCB_Remark(String CB_Remark) {
        this.CB_Remark = CB_Remark;
    }
}
