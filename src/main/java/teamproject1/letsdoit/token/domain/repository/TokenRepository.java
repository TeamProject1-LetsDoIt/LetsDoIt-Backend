package teamproject1.letsdoit.token.domain.repository;

import teamproject1.letsdoit.member.domain.Member;
import teamproject1.letsdoit.token.domain.Token;

import java.util.List;
import java.util.Optional;

public interface TokenRepository {

    Token save(Token token);

    Optional<Token> findByEmail(String email);

}
