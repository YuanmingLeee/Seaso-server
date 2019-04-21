package com.seaso.seaso.modules.sys.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Date;

public class JsonResponseBody<T> {

    private static final TypeReference<JsonResponseBody<?>> JsonResponseBodyType =
            new TypeReference<JsonResponseBody<?>>() {
            };

    private String message;

    private T data;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date timestamp;
    private int status;

    public JsonResponseBody(String message, int status, T data) {
        timestamp = new Date();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public JsonResponseBody(int status, T data) {
        this("success", status, data);
    }

    public JsonResponseBody(int status) {
        this(status, null);
    }

    public JsonResponseBody() {
        this(200);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static TypeReference<JsonResponseBody<?>> getJsonResponseBodyType() {
        return JsonResponseBodyType;
    }
}
