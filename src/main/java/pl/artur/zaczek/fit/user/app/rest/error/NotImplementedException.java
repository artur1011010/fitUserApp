package pl.artur.zaczek.fit.user.app.rest.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.NOT_IMPLEMENTED)
public class NotImplementedException extends BaseApiError{
    public NotImplementedException(String message, String code) {
        super(message, code);
    }

    public NotImplementedException(String message, String code, String details) {
        super(message, code, details);
    }
}
