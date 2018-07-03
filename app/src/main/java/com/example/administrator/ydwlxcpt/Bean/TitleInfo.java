package com.example.administrator.ydwlxcpt.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */

public class TitleInfo {

    private List<EntityTitleInfos> entityTitleInfos = new ArrayList<>();
    private List<EntityWashCarTypes> entityWashCarTypes = new ArrayList<>();

    @Override
    public String toString() {
        return "TitleInfo{" +
                "entityTitleInfos=" + entityTitleInfos +
                ", entityWashCarTypes=" + entityWashCarTypes +
                '}';
    }

    public TitleInfo() {
    }

    public List<EntityTitleInfos> getEntityTitleInfos() {

        return entityTitleInfos;
    }

    public void setEntityTitleInfos(List<EntityTitleInfos> entityTitleInfos) {
        this.entityTitleInfos = entityTitleInfos;
    }

    public List<EntityWashCarTypes> getEntityWashCarTypes() {
        return entityWashCarTypes;
    }

    public void setEntityWashCarTypes(List<EntityWashCarTypes> entityWashCarTypes) {
        this.entityWashCarTypes = entityWashCarTypes;
    }
}
