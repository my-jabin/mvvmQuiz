package com.jiujiu.question.data.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "category",indices = @Index("name"))
public class CategoryEntity {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    public int id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("totalNumberQuestions")
    @Expose
    public int totalNumberQuestions;
}
