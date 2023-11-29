package pl.artur.zaczek.fit.user.app.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ForbiddenException extends BaseApiError {

    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN.getReasonPhrase());
    }

    public ForbiddenException(String message, String details) {
        super(message, HttpStatus.FORBIDDEN.getReasonPhrase(), details);
    }

}
