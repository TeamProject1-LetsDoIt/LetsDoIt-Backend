package teamproject1.letsdoit.room.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SaveGroupReq {

    @Schema(type = "string", example = "string", description = "호스트 이메일")
    @NotBlank
    private String email;

    @Schema(type = "string", example = "string", description = "제목")
    @NotNull
    private String title;

    @Schema(type = "string", example = "string", description = "내용")
    @NotNull
    private String content;

    @Schema(type = "int", example = "5", description = "인원 제한")
    @NotNull
    private Integer maxPeople;

}
