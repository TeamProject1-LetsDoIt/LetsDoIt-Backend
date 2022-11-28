package teamproject1.letsdoit.notice.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamproject1.letsdoit.member.domain.Member;
import teamproject1.letsdoit.notice.domain.Notice;
import teamproject1.letsdoit.notice.domain.Type;
import teamproject1.letsdoit.notice.domain.repository.NoticeRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<Notice> getNotices(Member member) {
        List<Notice> notices = noticeRepository.findAll().stream()
                .filter(notice -> notice.getMember().equals(member) || notice.getType().equals(Type.ANNOUNCEMENT))
                .collect(Collectors.toList());
        Collections.reverse(notices);
        return notices;
    }

}
