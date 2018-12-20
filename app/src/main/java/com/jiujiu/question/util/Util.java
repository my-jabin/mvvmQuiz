package com.jiujiu.question.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.jiujiu.question.data.model.Difficulty;
import com.jiujiu.question.data.model.QuestionEntity;
import com.jiujiu.question.data.model.Type;
import com.jiujiu.question.data.remote.ApiResponse;
import com.jiujiu.question.data.remote.Question;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.stream.Collectors;

public final class Util {

    private Util() {

    }

    public static List<QuestionEntity> convertToQuestionEntities(List<Question> questions) {
        return questions.stream()
                .map(Question::toQuestionEntity)
                .collect(Collectors.toList());
    }
    public static String loadJSONFromAsset(Context context, String jsonFileName) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static String errorMessage(int errorCode) {
        switch (errorCode) {
            case ApiResponse.NO_RESULT:
                return "Could not return results. The API doesn't have enough questions for your query.";
            case ApiResponse.INVALID_PARAMETER:
                return "Contains an invalid parameter. Arguements passed in aren't valid.";
            case ApiResponse.TOKEN_NOT_FOUND:
                return " Session Token does not exist.";
            case ApiResponse.TOKEN_EMPTY:
                return "Session Token has returned all possible questions for the specified query. Resetting the Token is necessary.";
            default:
                return "";
        }
    }

    public static String decode(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Type convertToTypeEnum(String str) {
        if ("multiple".equalsIgnoreCase(str.trim()) || "Multiple Choice".equalsIgnoreCase(str)) {
            return Type.MultipleChoice;
        } else if ("boolean".equalsIgnoreCase(str.trim()) || "True/False".equalsIgnoreCase(str)) {
            return Type.TrueFalse;
        } else {
            return Type.Any;
        }
    }

    public static Difficulty convertToDifficultyEnum(String str) {
        if ("hard".equalsIgnoreCase(str.trim())) {
            return Difficulty.Hard;
        } else if ("easy".equalsIgnoreCase(str.trim())) {
            return Difficulty.Easy;
        } else if ("medium".equalsIgnoreCase(str.trim())) {
            return Difficulty.Medium;
        } else {
            return Difficulty.Any;
        }
    }

    public static String matchTypeToUrlQueryString(String str) {
        if (Type.MultipleChoice.toString().equalsIgnoreCase(str)) {
            return "multiple";
        } else if (Type.TrueFalse.toString().equalsIgnoreCase(str)) {
            return "boolean";
        } else {
            return "";
        }
    }

    public static String matchDifffToUrlQueryString(String str) {
        if (Difficulty.Hard.toString().equalsIgnoreCase(str)) {
            return "hard";
        } else if (Difficulty.Easy.toString().equalsIgnoreCase(str)) {
            return "easy";
        } else if (Difficulty.Medium.toString().equalsIgnoreCase(str)) {
            return "medium";
        } else {
            return "";
        }
    }
}
