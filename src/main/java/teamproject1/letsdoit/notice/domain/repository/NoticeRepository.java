package teamproject1.letsdoit.notice.domain.repository;

import teamproject1.letsdoit.member.domain.Member;
import teamproject1.letsdoit.notice.domain.Notice;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository {

    Notice save(Notice notice);

    Optional<Notice> findById(Long id);

    List<Notice> findByMember(Member member);

    List<Notice> findAll();
}
