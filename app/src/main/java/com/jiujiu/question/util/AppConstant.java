package com.jiujiu.question.util;

public final class AppConstant {

    private AppConstant(){

    }

    public static final String DATABASENAME = "template.db";
    public static final String PREFERENCENAME = "template_preference";
    public static final String USERNAME = "Steven Jobs";

    public static final String BASEURL = "https://opentdb.com/";
    public static final String API_COUNT_GLOBAL = BASEURL + "api_count_global.php";
    public static final String API_CATEGORY = BASEURL + "api_category.php";
    public static final String API_QUESTION_REQUEST = BASEURL+"api.php?";

    public static final String INTENT_QUIZ_CATEGORY = "category";
    public static final String INTENT_QUIZ_DIFFICULTY = "difficulty";
    public static final String INTENT_QUIZ_TYPE = "type";
    public static final String INTENT_QUIZ_AMOUNT = "amount";
    public static final String INTENT_QUIZ_MODE = "quiz_mode";

    public static final int QUIZ_MODE_NEW = 0;
    public static final int QUIZ_MODE_ABORD = 1;


}
