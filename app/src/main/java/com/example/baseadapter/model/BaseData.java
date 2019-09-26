package com.example.baseadapter.model;

public class BaseData<T> {
    public final Status status;
    public final T data;
    public final Throwable error;


    public BaseData(Status status, T data, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> BaseData<T> content(T data) {
        return new BaseData<>(Status.Content, data, null);
    }

    public static <T> BaseData<T> error(T data, Throwable error) {
        return new BaseData<>(Status.Error, data, error);
    }

    public static <T> BaseData<T> error(Throwable error) {
        return error(null, error);
    }

    public static <T> BaseData<T> error() {
        return error(null,null);
    }

    public static <T> BaseData<T> empty(T data) {
        return new BaseData<>(Status.Empty, data, null);
    }

    public static <T> BaseData<T> empty() {
        return empty(null);
    }

    public static <T> BaseData<T> loading(T data) {
        return new BaseData<>(Status.Loading, data, null);
    }

    public static <T> BaseData<T> loading() {
        return loading(null);
    }

    public static <T> BaseData<T> failure(Throwable failure) {
        return new BaseData<>(Status.Failure, null, failure);
    }

    public static <T> BaseData<T> errorMessage(T data) {
        return new BaseData<>(Status.ErrorMessage, data, null);
    }
}
