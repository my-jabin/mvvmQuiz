package com.jiujiu.question.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiCategoryResponse {

    @SerializedName("trivia_categories")
    @Expose
    private List<ApiCategory> categories = null;

    public List<ApiCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ApiCategory> triviaCategories) {
        this.categories = triviaCategories;
    }
}
