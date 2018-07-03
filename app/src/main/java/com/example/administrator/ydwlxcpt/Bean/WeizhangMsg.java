package com.example.administrator.ydwlxcpt.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/3.
 */

public class WeizhangMsg implements Serializable{

    private int status;
    private String msg;
    private WeizhangResult result;

    public WeizhangMsg() {
    }

    @Override
    public String toString() {
        return "WeizhangMsg{" +
                "msg='" + msg + '\'' +
                ", status=" + status +
                ", result=" + result +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public WeizhangResult getResult() {
        return result;
    }

    public void setResult(WeizhangResult result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
