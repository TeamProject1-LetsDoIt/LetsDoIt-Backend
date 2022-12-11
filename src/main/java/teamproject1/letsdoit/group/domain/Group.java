package teamproject1.letsdoit.group.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject1.letsdoit.common.domain.BaseEntity;
import teamproject1.letsdoit.member.domain.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group extends BaseEntity {

    private Long id;

    private Member hostMember;

    private String title;

    private String category;
    
    private String content;

    private Integer maxPeople;

    private Integer currentPeople;

    private List<Member> peopleList;

    private LocalDateTime expireTime;

    private Status status;

    @Builder
    public Group(Member hostMember, String title, String category, String content, Integer maxPeople, Integer currentPeople,  String expireTime) {
        this.hostMember = hostMember;
        this.title = title;
        this.category = category;
        this.content = content;
        this.maxPeople = maxPeople;
        this.currentPeople = currentPeople;
        this.peopleList = new ArrayList<>();
        peopleList.add(hostMember);
        this.expireTime = LocalDateTime.parse(expireTime);
        this.status = Status.ACTIVE;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addPeople(Member member) {
        if (currentPeople < maxPeople) {
            peopleList.add(member);
            currentPeople ++;
        }
    }

    public void deletePeople(Member member) {
        if (hostMember.equals(member) || !getPeopleList().contains(member)) {
            return;
        }
        getPeopleList().remove(member);
        currentPeople--;
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

    public void updateStatus(Status status) {
        this.status = status;
    }

}
