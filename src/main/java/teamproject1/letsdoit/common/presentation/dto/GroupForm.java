package teamproject1.letsdoit.common.presentation.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class GroupForm {

    @NotNull
    private String title;
    @NotNull
    private String content;
<<<<<<< HEAD
    @NotNull
    private String category;
    @NotNull
    private Integer maxPeople;
    @NotNull
=======
    private String category;
    private Integer maxPeople;
    private String date;
>>>>>>> main
    private String expireTime;

}
