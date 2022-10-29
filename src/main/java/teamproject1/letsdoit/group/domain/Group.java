package teamproject1.letsdoit.group.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject1.letsdoit.common.domain.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group extends BaseEntity {

    private Long id;

    private String hostEmail;

    private String title;

    private String category;
    
    private String content;

    private Integer maxPeople;

    private Integer currentPeople;

    private List<String> peopleList;

    private LocalDateTime expireTime;

    private Status status;

    @Builder
    public Group(String hostEmail, String title, String category, String content, Integer maxPeople, Integer currentPeople,  String expireTime) {
        this.hostEmail = hostEmail;
        this.title = title;
        this.category = category;
        this.content = content;
        this.maxPeople = maxPeople;
        this.currentPeople = currentPeople;
        this.peopleList = new ArrayList<>();
        peopleList.add(hostEmail);
        this.expireTime = LocalDateTime.parse(expireTime);
        this.status = Status.JOINABLE;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void countUp(){
        this.currentPeople++;
    }

    public void addPeople(String email) {
        peopleList.add(email);
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
