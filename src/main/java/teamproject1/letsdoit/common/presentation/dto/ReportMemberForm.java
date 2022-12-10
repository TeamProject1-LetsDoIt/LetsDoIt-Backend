package teamproject1.letsdoit.common.presentation.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReportMemberForm {

    @NotBlank
    @Email
    private String email;

    @NotNull
    private String content;


}
