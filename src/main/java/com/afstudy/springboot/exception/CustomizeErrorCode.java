package com.afstudy.springboot.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("你找的问题不存在");

    private String message;
    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message=message;
    }
}
