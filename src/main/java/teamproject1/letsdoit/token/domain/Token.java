package teamproject1.letsdoit.token.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject1.letsdoit.common.domain.BaseEntity;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token extends BaseEntity {

    private String userEmail;

    private String refreshToken;

    public Token updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    @Builder
    public Token(String userEmail, String refreshToken) {
        this.userEmail = userEmail;
        this.refreshToken = refreshToken;
    }

}