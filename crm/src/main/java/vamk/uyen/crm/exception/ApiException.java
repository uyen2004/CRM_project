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

    public ApiException(CommonErrorCode commonErrorCode) {
        this.httpStatus = commonErrorCode.status();
        this.message = commonErrorCode.message();
    }

}
