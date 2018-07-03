package com.example.administrator.ydwlxcpt.Bean;

/**
 * Created by Administrator on 2017/9/20.
 */

public class Xinghao {

    private int CBT_ID;                 //id
    private String CBT_FirstLetter;     //首字母
    private String CBT_BrandName;       //品牌名称
    private String CBT_BrandTypeName;    //型号名称
    private String CBT_VehicleClass;     //车辆类型
    private String CBT_Remark;           //备注

    public Xinghao() {
    }

    @Override
    public String toString() {
        return "Xinghao{" +
                "CBT_BrandName='" + CBT_BrandName + '\'' +
                ", CBT_ID=" + CBT_ID +
                ", CBT_FirstLetter='" + CBT_FirstLetter + '\'' +
                ", CBT_BrandTypeName='" + CBT_BrandTypeName + '\'' +
                ", CBT_VehicleClass='" + CBT_VehicleClass + '\'' +
                ", CBT_Remark='" + CBT_Remark + '\'' +
                '}';
    }

    public String getCBT_BrandName() {
        return CBT_BrandName;
    }

    public void setCBT_BrandName(String CBT_BrandName) {
        this.CBT_BrandName = CBT_BrandName;
    }

    public String getCBT_BrandTypeName() {
        return CBT_BrandTypeName;
    }

    public void setCBT_BrandTypeName(String CBT_BrandTypeName) {
        this.CBT_BrandTypeName = CBT_BrandTypeName;
    }

    public String getCBT_FirstLetter() {
        return CBT_FirstLetter;
    }

    public void setCBT_FirstLetter(String CBT_FirstLetter) {
        this.CBT_FirstLetter = CBT_FirstLetter;
    }

    public int getCBT_ID() {
        return CBT_ID;
    }

    public void setCBT_ID(int CBT_ID) {
        this.CBT_ID = CBT_ID;
    }

    public String getCBT_Remark() {
        return CBT_Remark;
    }

    public void setCBT_Remark(String CBT_Remark) {
        this.CBT_Remark = CBT_Remark;
    }

    public String getCBT_VehicleClass() {
        return CBT_VehicleClass;
    }

    public void setCBT_VehicleClass(String CBT_VehicleClass) {
        this.CBT_VehicleClass = CBT_VehicleClass;
    }
}
