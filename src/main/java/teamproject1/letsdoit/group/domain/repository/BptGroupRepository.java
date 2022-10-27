package teamproject1.letsdoit.group.domain.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import teamproject1.letsdoit.common.util.BPlusTree.BTree;
import teamproject1.letsdoit.group.domain.Group;

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
                + "\n" + "이메일: " + group.getHostEmail()
                + "\n" + "제목: " + group.getTitle()
                + "\n" + "카테코리: " + group.getCategory()
                + "\n" + "내용: " + group.getContent()
                + "\n" + "인원수: " + group.getMaxPeople()
<<<<<<< HEAD
                + "\n" + "만료 기한: " + group.getExpireTime());
=======
                + "\n" + "만료 기한" + group.getExpireTime());
>>>>>>> main
        return group;
    }

    @Override
    public Optional<Group> findById(Long id) {
        return Optional.ofNullable(bTree.search(Math.toIntExact(id)));
    }

    @Override
    public Optional<Group> findByEmail(String email) {
        return bTree.values().stream()
                .filter(group -> group.getHostEmail().equals(email)).findAny();
    }

    @Override
    public List<Group> findAll() {
        return new ArrayList<>(bTree.values());
    }
}
