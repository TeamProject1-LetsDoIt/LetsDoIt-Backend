package teamproject1.letsdoit.notice.domain.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import teamproject1.letsdoit.common.util.BPlusTree.BTree;
import teamproject1.letsdoit.member.domain.Member;
import teamproject1.letsdoit.notice.domain.Notice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class BptNoticeRepository implements NoticeRepository{

    private static BTree<Integer, Notice> bTree = new BTree<>();
    private static Long sequence = 0L;

    @Override
    public Notice save(Notice notice) {
        notice.setId(++sequence);
        bTree.insert(Math.toIntExact(notice.getId()), notice);
        log.info("\n" + "아이디: " + notice.getId() + "\n" + "제목: " + notice.getTitle() + "\n" + "내용: " + notice.getContent() + "\n" + "종류: " + notice.getType());
        return notice;
    }

    @Override
    public Optional<Notice> findById(Long id) {
        return Optional.ofNullable(bTree.search(Math.toIntExact(id)));
    }

    @Override
    public List<Notice> findByMember(Member member) {
        return bTree.values().stream()
                .filter(notice -> notice.getMember().equals(member))
                .collect(Collectors.toList());
    }

    @Override
    public List<Notice> findAll() {
        return new ArrayList<>(bTree.values());
    }
}
