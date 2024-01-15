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
    private String spec;

    public ApiException(CommonErrorCode commonErrorCode, String spec) {
        this.httpStatus = commonErrorCode.status();
        this.message = String.format("%s %s", spec,commonErrorCode.message());
    }

}