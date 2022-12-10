package teamproject1.letsdoit.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject1.letsdoit.common.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    private Long id;

    private String email;

    @JsonIgnore
    private String password;

    private String name;

    private String imageUrl;

    private Role role;

    private Integer reportCount;

    private Provider provider;

    private Status status;

    private String providerId;

    @Builder
    public Member(String email, String name, String imageUrl, Role role, Provider provider, String providerId) {
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.status = Status.ACTIVE;
        this.reportCount = 0;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void updateReportCount(){
        reportCount++;
        if (reportCount >= 5) {
            this.status = Status.BAN;
        }
    }

}
