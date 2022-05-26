package so.debateit.config;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import so.debateit.common.model.BoardSpecs;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    //Custom Error Hadler
    @ExceptionHandler(UserNotiException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(UserNotiException e) {
        logger.error("handleAllException", e);

        String errorMsg =  e.getErrorMsg();

        ErrorResponse response
                = new ErrorResponse(errorMsg, HttpStatus.NOT_ACCEPTABLE.toString());
        return new ResponseEntity<>(response, HttpStatus.resolve(HttpStatus.NOT_ACCEPTABLE.value()));
    }

    @Getter
    @Setter
    public class ErrorResponse {
        private String message;
        private String status;
        private String type = BoardSpecs.NotiType.NOTI.getValue();

        public ErrorResponse(String message, String status) {
            this.message = message;
            this.status = status;
        }
    }
}
