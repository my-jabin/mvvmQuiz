package com.jiujiu.question.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.List;

@Entity(tableName = "questions")
public class QuestionEntity {

    @PrimaryKey
    @NonNull
    public String question;

    public String category;

    public Difficulty difficulty;

    public Type type;

    @ColumnInfo(name = "correct_answer")
    public String correctAnswer;

    @ColumnInfo(name = "incorrect_answers")
    public List<String> inCorrectAnswer;

    @ColumnInfo(name = "answering_times")
    public int answeringTimes;

    @ColumnInfo(name = "correct_times")
    public int correctTimes;

    @ColumnInfo(name = "wrong_times")
    public int wrongTimes;

    @ColumnInfo(name = "is_answering")
    public boolean isAnswering;
}
