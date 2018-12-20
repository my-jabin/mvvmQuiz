package com.jiujiu.question.util;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.jiujiu.question.data.model.Difficulty;
import com.jiujiu.question.data.model.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Converter {

    @TypeConverter
    public static String DifficultEnumToString(Difficulty difficulty) {
        return difficulty.toString();
    }

    @TypeConverter
    public static Difficulty stringToDifficultyEnum(String s) {
        return Util.convertToDifficultyEnum(s);
    }

    @TypeConverter
    public static String typeEnumToString(Type type) {
        return type.toString();
    }

    @TypeConverter
    public static Type stringToTypeEnum(String s) {
        return Util.convertToTypeEnum(s);
    }

    @TypeConverter
    public static String answerListToString(List<String> answers) {
        String result = answers.stream().reduce("", (s1, s2) -> s1 + "," + s2);
        return result.substring(1, result.length());
    }

    @TypeConverter
    public static List<String> stringToAnswerList(String s) {
        return new ArrayList<>(Arrays.asList(s.split(",")));
    }
}
