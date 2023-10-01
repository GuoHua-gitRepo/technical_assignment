package com.example.technical_test.model;

import jakarta.persistence.*;
public class Response {

    int StatusCode;
    Object data;
    String message;

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "StatusCode=" + StatusCode +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
