package teamproject1.letsdoit.group.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group {

    private Long id;

    private String hostEmail;

    private String title;

    private String content;

    private Integer maxPeople;

    private Status status;

    @Builder
    public Group(String hostEmail, String title, String content, Integer maxPeople) {
        this.hostEmail = hostEmail;
        this.title = title;
        this.content = content;
        this.maxPeople = maxPeople;
        this.status = Status.JOINABLE;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

}
