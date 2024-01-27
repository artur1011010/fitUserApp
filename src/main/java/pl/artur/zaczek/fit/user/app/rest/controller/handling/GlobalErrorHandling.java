package pl.artur.zaczek.fit.user.app.rest.controller.handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.artur.zaczek.fit.user.app.rest.error.*;

@ControllerAdvice
public class GlobalErrorHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<ApiErrorResponse> handleNotFoundException(BaseApiError ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorMessage(ex));
    }

    @ExceptionHandler(value = BadRequestException.class)
    protected ResponseEntity<ApiErrorResponse> handleBadRequestException(BaseApiError ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorMessage(ex));
    }

    @ExceptionHandler(value = InternalServerErrorException.class)
    protected ResponseEntity<ApiErrorResponse> handleNotImplementedException(BaseApiError ex) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(createErrorMessage(ex));
    }

    private ApiErrorResponse createErrorMessage(BaseApiError ex){
        return ApiErrorResponse.builder()
                .message(ex.getMessage())
                .code(ex.getCode())
                .details(ex.getDetails())
                .build();
    }
}
