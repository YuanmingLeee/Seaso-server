package com.seaso.seaso.modules.sys.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class JsonResponseBody<T> {

    private static final TypeReference<JsonResponseBody<?>> JsonResponseBodyType =
            new TypeReference<JsonResponseBody<?>>() {
            };

    private String message;

    private T data;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date timestamp;
    @JsonProperty("status")
    private int statusCode;

    public JsonResponseBody(HttpStatus status, String message, T data) {
        timestamp = new Date();
        this.statusCode = status.value();
        this.message = message;
        this.data = data;
    }

    public JsonResponseBody(HttpStatus status, T data) {
        this(status, "success", data);
    }

    public JsonResponseBody(HttpStatus status) {
        this(status, null);
    }

    public JsonResponseBody() {
        this(HttpStatus.OK);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
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
