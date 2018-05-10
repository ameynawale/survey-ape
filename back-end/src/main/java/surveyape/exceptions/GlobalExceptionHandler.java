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

    @Value("${internal.server.error.message}")
    String internalErrorMsg;

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<?> internalErrorMsg() {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", internalErrorMsg);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpNotFoundException.class)
    public ResponseEntity<?> httpNotFound(HttpNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpBadRequestException.class)
    public ResponseEntity<?> httpBadRequest(HttpBadRequestException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpUnAuthorizedException.class)
    public ResponseEntity<?> httpUnAuthorized(HttpUnAuthorizedException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
