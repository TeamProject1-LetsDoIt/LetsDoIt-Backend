package teamproject1.letsdoit.member.domain.repository;

import teamproject1.letsdoit.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    List<Member> findAll();
}
