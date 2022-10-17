package teamproject1.letsdoit.room.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject1.letsdoit.room.domain.Group;
import teamproject1.letsdoit.room.domain.repository.GroupRepository;
import teamproject1.letsdoit.room.presentation.dto.SaveGroupReq;

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
