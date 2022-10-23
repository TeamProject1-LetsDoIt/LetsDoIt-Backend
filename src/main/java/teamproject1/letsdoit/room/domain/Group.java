package teamproject1.letsdoit.room.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject1.letsdoit.common.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group extends BaseEntity {

    private Long id;

    private String hostEmail;

    private String title;

    private String content;

    private Integer maxPeople;

    private Integer expireTime;

    private Status status;

    @Builder
    public Group(String hostEmail, String title, String content, Integer maxPeople, Integer expireTime) {
        this.hostEmail = hostEmail;
        this.title = title;
        this.content = content;
        this.maxPeople = maxPeople;
        this.expireTime = expireTime;
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
