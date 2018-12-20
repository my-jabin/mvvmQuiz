package com.jiujiu.question.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jiujiu.question.data.model.CategoryEntity;

public class ApiCategory {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryEntity toCategoryEntity(){
        CategoryEntity entity = new CategoryEntity();
        entity.id = this.id;
        entity.name = this.name;
        return entity;
    }
}
