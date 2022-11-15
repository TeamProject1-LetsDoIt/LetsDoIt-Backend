package teamproject1.letsdoit.common.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamproject1.letsdoit.common.exception.advice.assertThat.DefaultAssert;
import teamproject1.letsdoit.common.exception.advice.error.DefaultException;
import teamproject1.letsdoit.common.exception.advice.payload.ErrorCode;
import teamproject1.letsdoit.common.presentation.dto.Category;
import teamproject1.letsdoit.common.presentation.dto.GroupForm;
import teamproject1.letsdoit.member.application.MemberService;
import teamproject1.letsdoit.member.domain.Member;
import teamproject1.letsdoit.group.application.GroupService;
import teamproject1.letsdoit.group.domain.Group;
import teamproject1.letsdoit.member.domain.repository.MemberRepository;

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

    private final MemberRepository memberRepository;

    private final GroupService groupService;
    private final MemberService memberService;

    @GetMapping("/")
    public String beforeLoginForm() {

        return "beforeLogin";
    }

    @GetMapping("/error")
    public String error() {
        return "redirect:/login";
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

    @GetMapping("/me/createGroups")
    public String createGroups(Model model, HttpServletRequest request) {
        String userEmail = getEmail(request);
        Member member = memberService.findByMemberByEmail(userEmail);

        List<Group> createGroups = groupService.findCreateGroups(userEmail);

        model.addAttribute("member", member);
        model.addAttribute("groups", createGroups);

        return "createGroups";
    }

    @GetMapping("/me/joinGroups")
    public String joinGroups(Model model, HttpServletRequest request) {
        String userEmail = getEmail(request);
        Member member = memberService.findByMemberByEmail(userEmail);

        List<Group> joinGroups = groupService.findJoinGroups(userEmail);
        log.info(joinGroups.toString());

        model.addAttribute("member", member);
        model.addAttribute("groups", joinGroups);

        return "joinGroups";
    }

    @GetMapping("/me/joinGroups/{id}")
    public String joinGroupsInfo(@PathVariable Long id,  Model model, HttpServletRequest request) {
        String userEmail = getEmail(request);
        Member member = memberService.findByMemberByEmail(userEmail);

        Group group = groupService.findGroupById(id);

        model.addAttribute("member", member);
        model.addAttribute("group", group);

        return "participatedGroupInfo";
    }

    @DeleteMapping("/me/joinGroups/{id}")
    public String leaveGroup(@PathVariable Long id, HttpServletRequest request) {
        String userEmail = getEmail(request);
        Member member = memberService.findByMemberByEmail(userEmail);

        Group group = groupService.findGroupById(id);

        group.deletePeople(member);

        return "redirect:/me/joinGroups";
    }

    @GetMapping("/home")
    public String mainForm(Model model, HttpServletRequest request,
                           @RequestParam(value = "category", required = false) String category) {
        String userEmail = getEmail(request);
        Member member = memberService.findByMemberByEmail(userEmail);

        log.info(member.getEmail());
        List<Group> groups;


        if (category != null) {
            if (category.equals("expireTime")) {
                groups = groupService.sortGroupsByDeadline();
            }else {
                groups = groupService.sortGroupByCategory(category);
            }
        } else {
            groups = groupService.findGroups();
        }

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
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new DefaultException(ErrorCode.INVALID_CHECK, "존재하지 않는 멤버입니다"));

        Group group = Group.builder()
                .title(groupForm.getTitle())
                .content(groupForm.getContent())
                .category(groupForm.getCategory())
                .hostMember(member)
                .maxPeople(groupForm.getMaxPeople())
                .currentPeople(1)
                .expireTime(groupForm.getExpireTime())
                .build();

        groupService.saveGroup(group);

        return "redirect:/home";
    }

    @GetMapping("/group/{groupId}")
    public String seeGroup(@PathVariable Long groupId, Model model) {
        Group group = groupService.findGroupById(groupId);
        List<Member> participants = group.getPeopleList();

        model.addAttribute("participants", participants);
        model.addAttribute("member", group.getHostMember());
        model.addAttribute("group", group);

        return "groupInfo";
    }

    @PostMapping("/group/{groupId}")
    public String joinGroup(HttpServletRequest request, @PathVariable String groupId){
        String email = getEmail(request);
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new DefaultException(ErrorCode.INVALID_CHECK, "존재하지 않는 멤버입니다."));
        Group group = groupService.findGroupById(Long.valueOf(groupId));

        if(group.getPeopleList().stream().anyMatch(people -> people.getEmail().equals(email))){
            return "redirect:/home";
        }

        group.addPeople(member);

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
