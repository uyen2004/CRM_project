package vamk.uyen.crm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ApiException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;

    public ApiException(String msg, HttpStatus httpStatus, String message) {
        super(msg);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
