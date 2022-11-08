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

    public List<Group> findGroups() {
        List<Group> groups = groupRepository.findAll();
        Collections.reverse(groups);
        return groups;
    }

    public List<Group> findGroupsMemberJoined(String email) {
        List<Group> result = groupRepository.findAll().stream()
                .filter(group -> group.getPeopleList().stream().anyMatch(mail -> mail.equals(email)))
                .collect(Collectors.toList());
        log.info(result.toString());
        return result;
    }

    public List<Group> findGroupsMemberCreate(String email) {
        List<Group> result = groupRepository.findAll().stream()
                .filter(group -> group.getHostMember().getEmail().equals(email))
                .collect(Collectors.toList());
        log.info(result.toString());
        return result;
    }

    public List<Group> sortGroupsByDeadline() {
        List<Group> groups = groupRepository.findAll();
        groups.sort((b, a) -> (int) (ChronoUnit.SECONDS.between(a.getExpireTime(), LocalDateTime.now()) - ChronoUnit.SECONDS.between(b.getExpireTime(), LocalDateTime.now())));

        return groups;
    }

    public List<Group> sortGroupByCategory(String category) {

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
        return groups;
    }

    public List<Group> findCreateGroups(String email) {
        return groupRepository.findAll().stream()
                .filter(group -> group.getHostMember().getEmail().equals(email))
                .collect(Collectors.toList());
    }

    public List<Group> findJoinGroups(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new DefaultException(ErrorCode.INVALID_CHECK, "존재하지 않는 유저입니다."));
        List<Group> groups = groupRepository.findAll().stream()
                .filter(group -> group.getPeopleList()
                        .contains(member.getName()))
                .collect(Collectors.toList());
        log.info(groups.toString());
        return groups;
    }

}
