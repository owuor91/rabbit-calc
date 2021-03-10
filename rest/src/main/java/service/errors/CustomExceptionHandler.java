package service.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler({ DivisionByZeroException.class, NumberFormatException.class })
  public final ResponseEntity<ErrorResponse> handleExceptions(Exception e) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .message(e.getMessage())
        .error(true)
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .build();
    return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
  }
}
