package teamproject1.letsdoit.common.exception.advice.error;

import lombok.Getter;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import teamproject1.letsdoit.common.exception.advice.payload.ErrorCode;

import java.util.List;

@Getter
public class InvalidParameterException extends DefaultException{

    private final Errors errors;

    public InvalidParameterException(Errors errors) {
        super(ErrorCode.INVALID_PARAMETER);
        this.errors = errors;
    }

    public List<FieldError> getFieldErrors() {
        return errors.getFieldErrors();
    }
}
