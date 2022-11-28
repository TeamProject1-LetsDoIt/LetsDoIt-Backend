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

    private Member member;

    private String title;

    private String content;

    private boolean check;

    private Type type;

    @Builder
    public Notice(Long id, Member member, String title, String content, Type type) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.content = content;
        this.type = type;
        check = false;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void updateType(Type type) {
        this.type = type;
    }

    public void updateCheck(boolean check) {
        this.check = check;
    }
}
