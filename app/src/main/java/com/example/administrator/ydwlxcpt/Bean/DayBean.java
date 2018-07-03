package com.example.administrator.ydwlxcpt.Bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */

public class DayBean implements IPickerViewData {

    private String name;

    public DayBean(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
