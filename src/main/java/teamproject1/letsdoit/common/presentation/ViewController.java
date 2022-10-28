package teamproject1.letsdoit.common.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import teamproject1.letsdoit.common.exception.advice.assertThat.DefaultAssert;
import teamproject1.letsdoit.common.presentation.dto.Category;
import teamproject1.letsdoit.common.presentation.dto.GroupForm;
import teamproject1.letsdoit.member.application.MemberService;
import teamproject1.letsdoit.member.domain.Member;
import teamproject1.letsdoit.group.application.GroupService;
import teamproject1.letsdoit.group.domain.Group;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping("/me")
    public String myPage(Model model, HttpServletRequest request){
        String email = getEmail(request);
        Member member = memberService.findByMemberByEmail(email);
        List<Group> joinGroups = groupService.findGroupsMemberJoined(email);
        List<Group> createGroups = groupService.findGroupsMemberCreate(email);

        model.addAttribute("joinGroups", joinGroups);
        model.addAttribute("createGroups", createGroups);
        model.addAttribute("member", member);

        return "myPage";
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
        model.addAttribute("categories", Category.values());
        return "makeGroup";
    }

    @PostMapping("/home/new")
    public String groupCreate(@Valid GroupForm groupForm, HttpServletRequest request) {

        String email = getEmail(request);

        Group group = Group.builder()
                .title(groupForm.getTitle())
                .content(groupForm.getContent())
                .category(groupForm.getCategory())
                .hostEmail(email)
                .maxPeople(groupForm.getMaxPeople())
                .currentPeople(1)
                .expireTime(groupForm.getExpireTime())
                .build();

        groupService.saveGroup(group);

        return "redirect:/home";
    }

    @GetMapping("/group/{groupId}")
    public String seeGroup(@PathVariable("groupId") Long groupId, Model model) {
        Group group = groupService.findGroupById(groupId);
        Member member = memberService.findByMemberByEmail(group.getHostEmail());
        List<Member> participants =
                group.getPeopleList().stream().map(memberService::findByMemberByEmail).collect(Collectors.toList());
        log.info(participants.toString());

        model.addAttribute("participants", participants);
        model.addAttribute("member", member);
        model.addAttribute("group", group);

        return "groupInfo";
    }

    @PostMapping("/group/{groupId}")
    public String joinGroup(HttpServletRequest request, @PathVariable String groupId){
        String email = getEmail(request);
        Group group = groupService.findGroupById(Long.valueOf(groupId));

        if(group.getPeopleList().stream().anyMatch(people -> people.equals(email))){
            return "redirect:/home";
        }
        group.addPeople(email);
        group.countUp();

        return "redirect:/home";
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
