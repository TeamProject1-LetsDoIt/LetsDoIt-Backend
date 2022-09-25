package teamproject1.letsdoit.common.exception.advice.error;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;
import teamproject1.letsdoit.common.exception.advice.payload.ErrorCode;

@Getter
public class DefaultAuthenticationException extends AuthenticationException {

    private ErrorCode errorCode;

    public DefaultAuthenticationException(String msg, Throwable t) {
        super(msg, t);
        this.errorCode = ErrorCode.INVALID_REPRESENTATION;
    }

    public DefaultAuthenticationException(String msg) {
        super(msg);
    }

    public DefaultAuthenticationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
