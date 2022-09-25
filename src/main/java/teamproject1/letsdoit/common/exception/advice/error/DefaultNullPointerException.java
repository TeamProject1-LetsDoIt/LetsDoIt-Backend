package teamproject1.letsdoit.common.exception.advice.error;

import lombok.Getter;
import teamproject1.letsdoit.common.exception.advice.payload.ErrorCode;

@Getter
public class DefaultNullPointerException extends NullPointerException{

    private ErrorCode errorCode;

    public DefaultNullPointerException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
