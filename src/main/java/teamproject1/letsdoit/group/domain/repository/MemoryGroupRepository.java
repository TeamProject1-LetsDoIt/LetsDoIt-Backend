package teamproject1.letsdoit.group.domain.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import teamproject1.letsdoit.group.domain.Group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class MemoryGroupRepository implements GroupRepository{

    private static Map<Long, Group> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Group save(Group group) {
        group.setId(++sequence);
        store.put(group.getId(), group);
        log.info("\n" + "아이디: " + group.getId() + "\n" + "제목: " + group.getTitle() + "\n" + "내용: " + group.getContent() + "\n" + "인원수: " + group.getMaxPeople());
        return group;
    }

    @Override
    public Optional<Group> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Group> findByEmail(String email) {
        return null;
    }

    @Override
    public List<Group> findAll() {
        return null;
    }
}
