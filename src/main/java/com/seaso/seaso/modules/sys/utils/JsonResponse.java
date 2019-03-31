package com.seaso.seaso.modules.sys.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class JsonResponse<T> {

    @JsonProperty("status_code")
    private int statusCode;
    private String message;
    private T data;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date timestamp;

    public JsonResponse(HttpStatus status, String message, T data) {
        timestamp = new Date();
        this.statusCode = status.value();
        this.message = message;
        this.data = data;
    }

    public JsonResponse(T data) {
        this(HttpStatus.OK, "Success", data);
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
}
