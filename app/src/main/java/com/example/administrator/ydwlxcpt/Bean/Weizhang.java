package com.example.administrator.ydwlxcpt.Bean;

/**
 * Created by Administrator on 2017/11/3.
 */

public class Weizhang {

    private String time;
    private String address;
    private String content;
    private String legalnum;
    private int price;
    private int score;
    private String agency;
    private String handlefee;
    private String illegalid;
    private String province;
    private String city;
    private String town;
    private String lat;
    private String lng;
    private String canhandle;

    public Weizhang() {
    }

    @Override
    public String toString() {
        return "Weizhang{" +
                "address='" + address + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", legalnum='" + legalnum + '\'' +
                ", price=" + price +
                ", score=" + score +
                ", agency='" + agency + '\'' +
                ", handlefee='" + handlefee + '\'' +
                ", illegalid='" + illegalid + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", town='" + town + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", canhandle='" + canhandle + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getCanhandle() {
        return canhandle;
    }

    public void setCanhandle(String canhandle) {
        this.canhandle = canhandle;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHandlefee() {
        return handlefee;
    }

    public void setHandlefee(String handlefee) {
        this.handlefee = handlefee;
    }

    public String getIllegalid() {
        return illegalid;
    }

    public void setIllegalid(String illegalid) {
        this.illegalid = illegalid;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLegalnum() {
        return legalnum;
    }

    public void setLegalnum(String legalnum) {
        this.legalnum = legalnum;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
