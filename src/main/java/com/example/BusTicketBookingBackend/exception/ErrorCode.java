package com.example.BusTicketBookingBackend.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode{
    INVALID_KEY(999,"LỖI NHẬP SAI ERROR CODE", HttpStatus.INTERNAL_SERVER_ERROR),
    EMAIL_EXITS(1001,"email đã tồn tại trên hệ thống",HttpStatus.BAD_REQUEST),
    SDT_EXITS(1010,"Số điện thoại đã đăng ký",HttpStatus.BAD_REQUEST),
    UNKNOWN_ERROR(1002,"Unknown Error", HttpStatus.INTERNAL_SERVER_ERROR),
    EMAIL_INVALID(1003,"Email must contain @gmail.com",HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004,"Invalid MUST BE AT LESS 8 characters",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1005,"Không tìm thấy ng dùng",HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006,"Unauthenticated",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007,"You do not have permisson",HttpStatus.FORBIDDEN),
    DATA_NOT_FOUND(1008,"Khong tim thay du lieu",HttpStatus.NOT_FOUND),
    INVALID_FORMAT(1009,"Sai kiểu dữ liệu đầu vào",HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = httpStatusCode;
    }

    private int code;

    private HttpStatusCode statusCode;
    private String message;


}
