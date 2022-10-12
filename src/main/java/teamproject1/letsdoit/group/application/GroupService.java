package teamproject1.letsdoit.group.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject1.letsdoit.group.domain.Group;
import teamproject1.letsdoit.group.domain.repository.GroupRepository;
import teamproject1.letsdoit.group.presentation.dto.SaveGroupReq;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final GroupRepository groupRepository;

    public ResponseEntity<?> saveGroup(SaveGroupReq saveGroupReq) {
        Group group = Group.builder().hostEmail(saveGroupReq.getEmail())
                .title(saveGroupReq.getTitle())
                .content(saveGroupReq.getContent())
                .maxPeople(saveGroupReq.getMaxPeople())
                .build();

        groupRepository.save(group);
        return ResponseEntity.ok(group);

    }


}
