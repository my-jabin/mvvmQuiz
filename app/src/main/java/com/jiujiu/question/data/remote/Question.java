package com.jiujiu.question.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jiujiu.question.data.model.Difficulty;
import com.jiujiu.question.data.model.QuestionEntity;
import com.jiujiu.question.data.model.Type;
import com.jiujiu.question.util.Util;

import java.util.List;
import java.util.stream.Collectors;

public class Question {

    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("difficulty")
    @Expose
    private String difficulty;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("correct_answer")
    @Expose
    private String correctAnswer;
    @SerializedName("incorrect_answers")
    @Expose
    private List<String> incorrectAnswers = null;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(List<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public QuestionEntity toQuestionEntity() {
        QuestionEntity entity = new QuestionEntity();
        entity.category = Util.decode(this.category);
        entity.difficulty = Util.convertToDifficultyEnum(this.difficulty);
        entity.type = Util.convertToTypeEnum(this.type);
        entity.question = Util.decode(this.question);
        entity.correctAnswer = Util.decode(this.correctAnswer);
        entity.inCorrectAnswer =  this.incorrectAnswers.stream().map(Util::decode).collect(Collectors.toList());
        return entity;
    }
}