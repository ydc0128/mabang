package com.example.administrator.ydwlxcpt.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/18.
 */

public class RegisterPhone implements Serializable{

    private int U_LSID;                 //当未注册的手机号，返回一个唯一数字
    private String U_Tel;               //手机号
    private String U_IdentityID;        //验证码

    @Override
    public String toString() {
        return "RegisterPhone{" +
                "U_LSID=" + U_LSID +
                ", U_Tel='" + U_Tel + '\'' +
                ", U_IdentityID='" + U_IdentityID + '\'' +
                '}';
    }

    public RegisterPhone(int u_LSID, String u_Tel, String u_IdentityID) {
        U_LSID = u_LSID;
        U_Tel = u_Tel;
        U_IdentityID = u_IdentityID;
    }

    public RegisterPhone() {

    }

    public int getU_LSID() {

        return U_LSID;
    }

    public void setU_LSID(int u_LSID) {
        U_LSID = u_LSID;
    }

    public String getU_Tel() {
        return U_Tel;
    }

    public void setU_Tel(String u_Tel) {
        U_Tel = u_Tel;
    }

    public String getU_IdentityID() {
        return U_IdentityID;
    }

    public void setU_IdentityID(String u_IdentityID) {
        U_IdentityID = u_IdentityID;
    }
}
