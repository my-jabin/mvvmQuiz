package com.jiujiu.question.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {

    public static final int SUCCESS = 0;
    public static final int NO_RESULT = 1;
    public static final int INVALID_PARAMETER = 2;
    public static final int TOKEN_NOT_FOUND = 3;
    public static final int TOKEN_EMPTY = 4;

    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("results")
    @Expose
    private List<Question> questions = null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}