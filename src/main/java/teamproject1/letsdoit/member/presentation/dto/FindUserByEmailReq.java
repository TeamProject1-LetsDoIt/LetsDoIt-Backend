package teamproject1.letsdoit.member.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FindUserByEmailReq {

    @Schema(type = "string", example = "string", description = "이메일")
    @NotBlank
    private String email;

}
