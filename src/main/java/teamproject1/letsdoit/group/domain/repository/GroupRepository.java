package teamproject1.letsdoit.group.domain.repository;

import teamproject1.letsdoit.group.domain.Group;
import teamproject1.letsdoit.member.domain.Member;
import java.util.List;
import java.util.Optional;

public interface GroupRepository {

    Group save(Group member);

    Optional<Group> findById(Long id);

    Optional<Group> findByEmail(String email);

    List<Group> findAll();

    void deleteById(Long id);

    Optional<Group> forceFindById(Long id);
}