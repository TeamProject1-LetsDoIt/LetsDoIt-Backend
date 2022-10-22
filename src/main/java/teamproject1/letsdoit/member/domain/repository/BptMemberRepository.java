package teamproject1.letsdoit.member.domain.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import teamproject1.letsdoit.common.util.BPlusTree.BTree;
import teamproject1.letsdoit.member.domain.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class BptMemberRepository implements MemberRepository {

    private static BTree<Integer, Member> bTree = new BTree<>();
    private static Long sequence = 0L;

    @Override
    public Member save(Member member) {
        if (findByEmail(member.getEmail()).isPresent()) {
            throw new IllegalStateException();
        }
        member.setId(++sequence);
        bTree.insert(Math.toIntExact(member.getId()), member);
        log.info("\n" + "아이디: " + member.getId() + "\n" + "이메일: " + member.getEmail() + "\n" + "이름: " + member.getName() + "\n" + "프로필사진: " + member.getImageUrl());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(bTree.search(Math.toIntExact(id)));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return bTree.values().stream()
                .filter(member -> member.getEmail().equals(email)).findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(bTree.values());
    }

}
