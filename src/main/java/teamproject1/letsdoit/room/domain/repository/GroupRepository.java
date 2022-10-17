package teamproject1.letsdoit.room.domain.repository;

import teamproject1.letsdoit.room.domain.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {

    Group save(Group group);

    Optional<Group> findById(Long id);

    List<Group> findAll();

}
