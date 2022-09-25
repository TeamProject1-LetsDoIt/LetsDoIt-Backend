package teamproject1.letsdoit.token.domain.repository;

import org.springframework.stereotype.Repository;
import teamproject1.letsdoit.token.domain.Token;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryTokenRepository implements TokenRepository{

    private static Map<String, Token> store = new HashMap<>();

    @Override
    public Token save(Token token) {
        store.put(token.getUserEmail(), token);
        return token;
    }

    @Override
    public Optional<Token> findByEmail(String email) {
        return store.values().stream()
                .filter(token -> token.getUserEmail().equals(email)).findAny();
    }

    public void clearStore(){
        store.clear();
    }

}
