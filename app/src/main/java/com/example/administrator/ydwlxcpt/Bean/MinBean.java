package com.example.administrator.ydwlxcpt.Bean;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by Administrator on 2017/8/8.
 */

public class MinBean implements IPickerViewData {

    private String name;

    public MinBean(String name){
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
