package teamproject1.letsdoit.common.exception.advice.error;

import lombok.Getter;
import teamproject1.letsdoit.common.exception.advice.payload.ErrorCode;

@Getter
public class DefaultException extends RuntimeException {

    private ErrorCode errorCode;

    public DefaultException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public DefaultException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
