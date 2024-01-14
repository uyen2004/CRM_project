package vamk.uyen.crm.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCodeException implements CommonErrorCode {

    SUCCESS(HttpStatus.OK, "Success"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Could not found");

    private final HttpStatus status;
    private final String message;

    private ErrorCodeException(HttpStatus httpStatus, String message) {
        this.status = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus status() {
        return this.status;
    }

    @Override
    public String message() {
        return this.message;
    }
}
