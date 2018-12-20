package com.jiujiu.question.data.model;

import androidx.room.Dao;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class Resource<T> {

    @NonNull
    private Status status;

    @NonNull
    private String message;

    @NonNull
    private T data;

    public Resource(@Nullable T data, @NonNull Status status, @Nullable String message) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> Resource<T> success(@NonNull T data){
        return new Resource<>(data, Status.SUCCESS, null);
    }

    public static <T> Resource<T> error(String message, @Nullable T data){
        return new Resource<T>(data,Status.ERROR, message);
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
