package teamproject1.letsdoit.group.domain.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import teamproject1.letsdoit.common.util.BPlusTree.BTree;
import teamproject1.letsdoit.group.domain.Group;
import teamproject1.letsdoit.group.domain.Status;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class BptGroupRepository implements GroupRepository{

    private static BTree<Integer, Group> bTree = new BTree<>();
    private static Long sequence = 0L;

    @Override
    public Group save(Group group) {
        group.setId(++sequence);
        bTree.insert(Math.toIntExact(group.getId()), group);
        log.info("\n" + "아이디: " + group.getId()
                + "\n" + "이메일: " + group.getHostMember().getEmail()
                + "\n" + "제목: " + group.getTitle()
                + "\n" + "카테코리: " + group.getCategory()
                + "\n" + "내용: " + group.getContent()
                + "\n" + "인원수: " + group.getMaxPeople()
                + "\n" + "만료 기한: " + group.getExpireTime());
        return group;
    }

    @Override
    public Optional<Group> findById(Long id) {
        return bTree.values().stream()
                .filter(group -> group.getStatus().equals(Status.ACTIVE) && group.getId().equals(id)).findAny();
    }

    @Override
    public Optional<Group> findByEmail(String email) {
        return bTree.values().stream()
                .filter(group -> group.getStatus().equals(Status.ACTIVE) && group.getHostMember().getEmail().equals(email)).findAny();
    }

    @Override
    public List<Group> findAll() {
        List<Group> groups = bTree.values();
        groups.removeIf(group -> group.getExpireTime().isBefore(LocalDateTime.now()) || group.getStatus().equals(Status.DELETE));
        return groups;
    }

    @Override
    public void deleteById(Long id) {
        Group group = bTree.search(Math.toIntExact(id));
        group.updateStatus(Status.DELETE);
    }
}
