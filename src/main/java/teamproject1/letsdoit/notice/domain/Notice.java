package teamproject1.letsdoit.notice.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject1.letsdoit.common.domain.BaseEntity;
import teamproject1.letsdoit.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {

    private Long id;

    private Long groupId;

    private Member member;

    private String title;

    private String content;

    private Integer check;

    private Status status;

    private Type type;

    @Builder
    public Notice(Long id, Long groupId, Member member, String title, String content, Type type) {
        this.id = id;
        this.groupId = groupId;
        this.member = member;
        this.title = title;
        this.content = content;
        this.type = type;
        check = 0;
        status = Status.ACTIVE;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void updateType(Type type) {
        this.type = type;
    }

    public void updateCheck(Integer check) {
        this.check = check;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}
