package com.example.BusTicketBookingBackend.exception;


public enum ErrorCode{
    INVALID_KEY(999,"LỖI NHẬP SAI ERROR CODE"),
    EMAIL_EXITS(1001,"email đã tồn tại trên hệ thống"),
    UNKNOWN_ERROR(1002,"Unknown Error"),
    EMAIL_INVALID(1003,"Email must contain @gmail.com"),
    INVALID_PASSWORD(1004,"Invalid MUST BE AT LESS 8 characters"),
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
