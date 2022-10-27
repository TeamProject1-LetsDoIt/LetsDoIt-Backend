package teamproject1.letsdoit.common.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import teamproject1.letsdoit.common.exception.advice.assertThat.DefaultAssert;
import teamproject1.letsdoit.common.presentation.dto.GroupForm;
import teamproject1.letsdoit.member.application.MemberService;
import teamproject1.letsdoit.member.domain.Member;
import teamproject1.letsdoit.group.application.GroupService;
import teamproject1.letsdoit.group.domain.Group;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    public String mainForm(Model model, HttpServletRequest request) {
        String userEmail = getEmail(request);

        List<Group> groups = groupService.findGroups();
        Member member = memberService.findByMemberByEmail(userEmail);
        log.info(member.getEmail());

        model.addAttribute("groups", groups);
        model.addAttribute("member", member);

        return "home";
    }


    @GetMapping("/home/new")
    public String groupCreateForm(Model model) {
        model.addAttribute("group", new GroupForm());
        return "makeGroup";
    }

    @PostMapping("/home/new")
    public String groupCreate(@Valid GroupForm groupForm, HttpServletRequest request) {

        String email = getEmail(request);

        Group group = Group.builder()
                .title(groupForm.getTitle())
                .content(groupForm.getContent())
                .hostEmail(email)
                .maxPeople(groupForm.getMaxPeople())
                .currentPeople(0)
                .expireTime(groupForm.getExpireTime())
                .build();

        groupService.saveGroup(group);

        return "redirect:/home";
    }

    @GetMapping("/group/{groupId}")
    public String seeGroup(@PathVariable("groupId") Long groupId, Model model) {
        log.info("groupId: ", groupId);
        Group group = groupService.findGroupById(groupId);
        Member member = memberService.findByMemberByEmail(group.getHostEmail());
        model.addAttribute("member", member);
        model.addAttribute("group", group);

        return "groupInfo";
    }

    private static String getEmail(HttpServletRequest request) {
        String userEmail = "";
        Cookie[] cookies = request.getCookies();

        Optional<Cookie> emailCookie = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("email")).findAny();
        DefaultAssert.isOptionalPresent(emailCookie);
        userEmail = emailCookie.get().getValue();
        return userEmail;
    }

}
