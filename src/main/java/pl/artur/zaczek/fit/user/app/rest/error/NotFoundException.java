package pl.artur.zaczek.fit.user.app.rest.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseApiError {

    public NotFoundException(String message, String code) {
        super(message, code);
    }

    public NotFoundException(String message, String code, String details) {
        super(message, code, details);
    }
}
