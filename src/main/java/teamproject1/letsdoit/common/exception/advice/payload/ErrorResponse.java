package teamproject1.letsdoit.common.exception.advice.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {
    private LocalDateTime timestamp = LocalDateTime.now();

    private String message;

    private String code;

    @JsonProperty("class")
    private String clazz;

    private int status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("errors")
    private List<CustomFieldError> customFieldErrors = new ArrayList<>();

    public ErrorResponse() {}

    @Builder
    public ErrorResponse(String message, String code, String clazz, int status, List<FieldError> fieldErrors) {
        this.message = message;
        this.code = code;
        this.clazz = clazz;
        this.status = status;
        setFieldErrors(fieldErrors);
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        if (fieldErrors != null) {
            fieldErrors.forEach(error -> {
                customFieldErrors.add(new CustomFieldError(
                        error.getField(),
                        error.getRejectedValue(),
                        error.getDefaultMessage()
                ));
            });
        }
    }

    public static class CustomFieldError {

        private String field;
        private Object value;
        private String reason;

        public CustomFieldError(String field, Object value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public String getField() {
            return field;
        }

        public Object getValue() {
            return value;
        }

        public String getReason() {
            return reason;
        }
    }
}

