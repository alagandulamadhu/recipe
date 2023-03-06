package nl.abnamro.intake.assesement.recipe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRecipeNotFoundException(Exception exception, WebRequest request) {
        return buildExceptionResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(RecipeConstraintException.class)
    public ResponseEntity<ExceptionResponse> handleRecipeConstraintException(Exception exception, WebRequest request) {
        return buildExceptionResponse(HttpStatus.CONFLICT, exception.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllException(Exception exception, WebRequest request) {
        return buildExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request.getDescription(false));
    }

    private ResponseEntity<ExceptionResponse> buildExceptionResponse(HttpStatus httpStatus, String message, String detail) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setTimestamp(new Date());
        exceptionResponse.setStatus(httpStatus.value());
        exceptionResponse.setMessage(message);
        exceptionResponse.setDetail(detail);
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }
}
