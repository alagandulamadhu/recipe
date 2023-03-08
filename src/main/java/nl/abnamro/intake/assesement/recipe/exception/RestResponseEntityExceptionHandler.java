package nl.abnamro.intake.assesement.recipe.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<Object> handleRecipeNotFoundException(Exception exception, WebRequest request) {
        return buildExceptionResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(RecipeConstraintException.class)
    public ResponseEntity<Object> handleRecipeConstraintException(Exception exception, WebRequest request) {
        return buildExceptionResponse(HttpStatus.CONFLICT, exception.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception exception, WebRequest request) {
        return buildExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request.getDescription(false));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errorsMap = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(objectError -> {
            errorsMap.put(((FieldError) objectError).getField(), objectError.getDefaultMessage());
        });
        return buildExceptionResponse(status, "Validation Failed", errorsMap.toString());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildExceptionResponse(status, exception.getMessage(), exception.getLocalizedMessage());
    }

    private ResponseEntity<Object> buildExceptionResponse(HttpStatus httpStatus, String message, String detail) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setTimestamp(new Date());
        exceptionResponse.setStatus(httpStatus.value());
        exceptionResponse.setMessage(message);
        exceptionResponse.setDetail(detail);
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }
}
