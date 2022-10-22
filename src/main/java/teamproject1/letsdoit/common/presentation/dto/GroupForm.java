package teamproject1.letsdoit.common.presentation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GroupForm {

    private String title;
    private String content;
    private String email;
    private int memberNum;
    private int expireTime;

}
