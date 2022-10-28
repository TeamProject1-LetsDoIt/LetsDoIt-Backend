package teamproject1.letsdoit.group.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject1.letsdoit.common.exception.advice.assertThat.DefaultAssert;
import teamproject1.letsdoit.group.domain.Group;
import teamproject1.letsdoit.group.domain.repository.GroupRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final GroupRepository groupRepository;

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
                .filter(group -> group.getHostEmail().equals(email))
                .collect(Collectors.toList());
        log.info(result.toString());
        return result;
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

    public List<Group> findGroupsMemberJoined (String email) {
        List<Group> result = groupRepository.findAll().stream()
                .filter(group -> group.getPeopleList().stream().anyMatch(mail -> mail.equals(email)))
                        .collect(Collectors.toList());
        log.info(result.toString());
        return result;
    }

    public List<Group> findGroupsMemberCreate (String email) {
        List<Group> result = groupRepository.findAll().stream()
                .filter(group -> group.getHostEmail().equals(email))
                .collect(Collectors.toList());
        log.info(result.toString());
        return result;
    }


}
