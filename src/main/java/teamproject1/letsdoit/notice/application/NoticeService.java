package teamproject1.letsdoit.notice.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamproject1.letsdoit.group.domain.Group;
import teamproject1.letsdoit.member.domain.Member;
import teamproject1.letsdoit.notice.domain.Notice;
import teamproject1.letsdoit.notice.domain.Type;
import teamproject1.letsdoit.notice.domain.repository.NoticeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<Notice> getNotices(Member member, Integer page) {
        List<Notice> notices = noticeRepository.findAll().stream()
                .filter(notice -> notice.getMember().equals(member) || notice.getType().equals(Type.ANNOUNCEMENT))
                .collect(Collectors.toList());
        Collections.reverse(notices);
        return noticePaging(notices, page);
    }

    private List<Notice> noticePaging(List<Notice> notices, Integer page) {
        List<Notice> resultNotices = new ArrayList<>();
        if ((notices.size() / 8) + 1 < page) {
            return null;
        }
        int index = (page - 1) * 8;
        if (notices.size() / (page * 8) >= 1) {
            for (int i = index; i < index + 8; i++) {
                resultNotices.add(notices.get(i));
            }
        } else {
            for (int i = index; i < notices.size(); i++) {
                resultNotices.add(notices.get(i));
            }
        }
        return resultNotices;
    }

}
