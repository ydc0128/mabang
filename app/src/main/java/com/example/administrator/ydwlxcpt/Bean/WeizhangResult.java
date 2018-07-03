package com.example.administrator.ydwlxcpt.Bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/3.
 */

public class WeizhangResult {

    private String lsprefix;
    private String lsnum;
    private String carorg;
    private String usercarid;
    private int count;
    private int totalprice;
    private int totalscore;
    private List<Weizhang> list;

    public WeizhangResult() {
    }

    @Override
    public String toString() {
        return "WeizhangResult{" +
                "carorg='" + carorg + '\'' +
                ", lsprefix='" + lsprefix + '\'' +
                ", lsnum='" + lsnum + '\'' +
                ", usercarid='" + usercarid + '\'' +
                ", count=" + count +
                ", totalprice=" + totalprice +
                ", totalscore=" + totalscore +
                ", list=" + list +
                '}';
    }

    public String getCarorg() {
        return carorg;
    }

    public void setCarorg(String carorg) {
        this.carorg = carorg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Weizhang> getList() {
        return list;
    }

    public void setList(List<Weizhang> list) {
        this.list = list;
    }

    public String getLsnum() {
        return lsnum;
    }

    public void setLsnum(String lsnum) {
        this.lsnum = lsnum;
    }

    public String getLsprefix() {
        return lsprefix;
    }

    public void setLsprefix(String lsprefix) {
        this.lsprefix = lsprefix;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public int getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(int totalscore) {
        this.totalscore = totalscore;
    }

    public String getUsercarid() {
        return usercarid;
    }

    public void setUsercarid(String usercarid) {
        this.usercarid = usercarid;
    }
}
