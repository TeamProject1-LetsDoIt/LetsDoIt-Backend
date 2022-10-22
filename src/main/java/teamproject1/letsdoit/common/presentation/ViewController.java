package teamproject1.letsdoit.common.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import teamproject1.letsdoit.common.presentation.dto.GroupForm;
import teamproject1.letsdoit.member.application.MemberService;
import teamproject1.letsdoit.member.domain.Member;
import teamproject1.letsdoit.room.application.GroupService;
import teamproject1.letsdoit.room.domain.Group;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ViewController {

    private final GroupService groupService;
    private final MemberService memberService;

    @GetMapping("/")
    public String beforeLoginForm() {

        return "beforeLogin";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/home")
    public String mainForm(Model model) {
        List<Group> groups = groupService.findGroups();
        List<Member> members = memberService.findMembers();

        model.addAttribute("groups", groups);
        model.addAttribute("members", members);

        return "home";
    }

    @PostMapping("/home/new")
    public String roomCreate(GroupForm groupForm) {
        Group group = Group.builder()
                .title(groupForm.getTitle())
                .content(groupForm.getContent())
                .hostEmail(groupForm.getEmail())
                .maxPeople(groupForm.getMemberNum())
                .expireTime(groupForm.getExpireTime())
                .build();

        groupService.saveGroup(group);

        return "redirect:/home";
    }

}
