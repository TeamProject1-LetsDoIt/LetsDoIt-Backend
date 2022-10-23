package teamproject1.letsdoit.common.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import teamproject1.letsdoit.common.exception.advice.assertThat.DefaultAssert;
import teamproject1.letsdoit.common.presentation.dto.GroupForm;
import teamproject1.letsdoit.member.application.MemberService;
import teamproject1.letsdoit.member.domain.Member;
import teamproject1.letsdoit.group.application.GroupService;
import teamproject1.letsdoit.group.domain.Group;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
        String userEmail = "";
        Cookie[] cookies = request.getCookies();

        Optional<Cookie> emailCookie = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("email")).findAny();
        DefaultAssert.isOptionalPresent(emailCookie);
        userEmail = emailCookie.get().getValue();

        List<Group> groups = groupService.findGroups();
        Optional<Member> result = memberService.findByMemberByEmail(userEmail);
        DefaultAssert.isOptionalPresent(result);
        log.info(result.get().getEmail());

        Member member = result.get();

        model.addAttribute("groups", groups);
        model.addAttribute("member", member);

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
