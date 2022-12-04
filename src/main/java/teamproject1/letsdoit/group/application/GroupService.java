package teamproject1.letsdoit.group.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject1.letsdoit.common.exception.advice.assertThat.DefaultAssert;
import teamproject1.letsdoit.common.exception.advice.error.DefaultException;
import teamproject1.letsdoit.common.exception.advice.payload.ErrorCode;
import teamproject1.letsdoit.group.domain.Group;
import teamproject1.letsdoit.group.domain.repository.GroupRepository;
import teamproject1.letsdoit.member.domain.Member;
import teamproject1.letsdoit.member.domain.repository.MemberRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    public void saveGroup(Group group) {
        groupRepository.save(group);
    }

    public Group findGroupById(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        DefaultAssert.isOptionalPresent(group);
        return group.get();
    }

    public List<Group> findGroups(Integer page) {
        List<Group> groups = groupRepository.findAll();
        Collections.reverse(groups);
        return homePaging(groups, page);
    }

    public List<Group> findGroupsMemberJoined(String email) {
        List<Group> result = groupRepository.findAll().stream()
                .filter(group -> group.getPeopleList().stream().anyMatch(mail -> mail.equals(email)))
                .collect(Collectors.toList());
        return result;
    }

    public List<Group> findGroupsMemberCreate(String email) {
        List<Group> result = groupRepository.findAll().stream()
                .filter(group -> group.getHostMember().getEmail().equals(email))
                .collect(Collectors.toList());
        return result;
    }

    public List<Group> sortGroupsByDeadline(Integer page) {
        List<Group> groups = groupRepository.findAll();
        groups.sort((b, a) -> (int) (ChronoUnit.SECONDS.between(a.getExpireTime(), LocalDateTime.now()) - ChronoUnit.SECONDS.between(b.getExpireTime(), LocalDateTime.now())));

        return homePaging(groups, page);
    }

    public List<Group> findGroupsBySearch(String search, Integer page) {
        List<Group> result = groupRepository.findAll().stream()
                .filter(group -> group.getTitle().contains(search))
                .collect(Collectors.toList());
        Collections.reverse(result);
        return homePaging(result, page);
    }

    public List<Group> sortGroupByCategory(String category, Integer page) {

        String result = null;

        switch (category) {
            case "study":
                result = "공부합시다";
                break;
            case "exercise":
                result = "운동합시다";
                break;
            case "meal":
                result = "식사합시다";
                break;
            default:
                result = "";
        }

        String finalResult = result;

        List<Group> groups = groupRepository.findAll()
                .stream()
                .filter(group -> group.getCategory().equals(finalResult))
                .collect(Collectors.toList());

        Collections.reverse(groups);
        return homePaging(groups, page);
    }

    public List<Group> findCreateGroups(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new DefaultException(ErrorCode.INVALID_CHECK, "존재하지 않는 유저입니다"));
        return groupRepository.findAll().stream()
                .filter(group -> group.getHostMember().equals(member))
                .collect(Collectors.toList());
    }

    public List<Group> findJoinGroups(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new DefaultException(ErrorCode.INVALID_CHECK, "존재하지 않는 유저입니다."));
        return groupRepository.findAll().stream()
                .filter(group -> group.getPeopleList().contains(member) && !group.getHostMember().equals(member))
                .collect(Collectors.toList());
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    private List<Group> homePaging(List<Group> groups, Integer page) {
        List<Group> resultGroups = new ArrayList<>();
        if ((groups.size() / 10) + 1 < page) {
            return null;
        }
        int index = (page - 1) * 10;
        if (groups.size() / (page * 10) >= 1) {
            for (int i = index; i < index + 10; i++) {
                resultGroups.add(groups.get(i));
            }
        } else {
            for (int i = index; i < groups.size(); i++) {
                resultGroups.add(groups.get(i));
            }
        }
        return resultGroups;
    }

    private List<Group> myPagePaging(List<Group> groups, Integer page) {
        List<Group> resultGroups = new ArrayList<>();
        if ((groups.size() / 8) + 1 < page) {
            return null;
        }
        int index = (page - 1) * 8;
        if (groups.size() / (page * 8) >= 1) {
            for (int i = index; i < index + 8; i++) {
                resultGroups.add(groups.get(i));
            }
        } else {
            for (int i = index; i < groups.size(); i++) {
                resultGroups.add(groups.get(i));
            }
        }
        return resultGroups;
    }
}
