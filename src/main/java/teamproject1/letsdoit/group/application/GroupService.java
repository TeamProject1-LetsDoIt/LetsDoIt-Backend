package teamproject1.letsdoit.group.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject1.letsdoit.group.domain.Group;
import teamproject1.letsdoit.group.domain.repository.GroupRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final GroupRepository groupRepository;

    public void saveGroup(Group group) {
        groupRepository.save(group);
    }

    public List<Group> findGroups() {
        return groupRepository.findAll();
    }


}
