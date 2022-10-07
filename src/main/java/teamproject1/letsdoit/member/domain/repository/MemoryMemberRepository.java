package teamproject1.letsdoit.member.domain.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import teamproject1.letsdoit.member.domain.Member;

import java.util.*;

@Repository
@Slf4j
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; 

    @Override
    public Member save(Member member) {
        if(findByEmail(member.getEmail()).isPresent()){
            throw new IllegalStateException();
        }
        member.setId(++sequence);
        store.put(member.getId(), member);
        log.info(member.getId() + " " + member.getEmail() + " " + member.getName() + " " + member.getImageUrl());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return store.values().stream()
                .filter(member -> member.getEmail().equals(email)).findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }

}
