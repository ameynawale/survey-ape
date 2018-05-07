package surveyape.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${invalid.session.message}")
    String invalidSessionMsg;

    @ExceptionHandler(InvalidSessionException.class)
    public ResponseEntity<?> invalidSession() {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", invalidSessionMsg);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
