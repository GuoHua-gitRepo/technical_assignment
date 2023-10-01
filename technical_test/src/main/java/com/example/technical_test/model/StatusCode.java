package com.example.technical_test.model;

public enum StatusCode {
    NOT_FOUND(404,"Result Not Found"),
    OK(200,"Record Retrieved"),
    MissingBody(400,"Missing Body");

    private final int statusCode;
    private final String Message;

    StatusCode(int statusCode, String message) {
        this.statusCode = statusCode;
        Message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return Message;
    }

    @Override
    public String toString() {
        return "StatusCode{" +
                "statusCode=" + statusCode +
                ", Message='" + Message + '\'' +
                '}';
    }
}
