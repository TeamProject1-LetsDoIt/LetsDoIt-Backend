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
import teamproject1.letsdoit.common.presentation.dto.ReportMemberForm;
import teamproject1.letsdoit.group.domain.repository.GroupRepository;
import teamproject1.letsdoit.member.application.MemberService;
import teamproject1.letsdoit.member.domain.Member;
import teamproject1.letsdoit.group.application.GroupService;
import teamproject1.letsdoit.group.domain.Group;
import teamproject1.letsdoit.member.domain.repository.MemberRepository;
import teamproject1.letsdoit.notice.application.NoticeService;
import teamproject1.letsdoit.notice.domain.Notice;
import teamproject1.letsdoit.notice.domain.Status;
import teamproject1.letsdoit.notice.domain.Type;
import teamproject1.letsdoit.notice.domain.repository.NoticeRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static teamproject1.letsdoit.member.domain.Status.BAN;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ViewController {

    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;
    private final GroupRepository groupRepository;

    private final GroupService groupService;
    private final MemberService memberService;
    private final NoticeService noticeService;

    @GetMapping("/")
    public String beforeLoginForm() {

        return "beforeLogin";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/me")
    public String myPage(Model model, HttpServletRequest request) {
        String email = getEmail(request);
        Member member = memberService.findByMemberByEmail(email);
        List<Group> joinGroups = groupService.findGroupsMemberJoined(email);
        List<Group> createGroups = groupService.findGroupsMemberCreate(email);

        model.addAttribute("joinGroups", joinGroups);
        model.addAttribute("createGroups", createGroups);
        model.addAttribute("member", member);

        return "myPage";
    }

    @GetMapping("/help")
    public String help(Model model, HttpServletRequest request) {
        String email = getEmail(request);
        Member member = memberService.findByMemberByEmail(email);

        model.addAttribute("member", member);

        return "help";
    }

    @GetMapping("/report")
    public String report(Model model) {

        model.addAttribute("reportMember", new ReportMemberForm());

        return "report";
    }

    @PostMapping("/report")
    public String postReport(@Valid ReportMemberForm reportMemberForm, HttpServletRequest request) {
        String userEmail = getEmail(request);
        Member member = memberService.findByMemberByEmail(reportMemberForm.getEmail());

        member.updateReportCount();

        return "redirect:/home";
    }

    @GetMapping("/me/joinGroups")
    public String joinGroups(Model model, HttpServletRequest request,
                             @RequestParam(value = "page", required = false) Integer page) {
        String userEmail = getEmail(request);
        Member member = memberService.findByMemberByEmail(userEmail);

        Integer homePage = page;
        if (homePage == null) {
            homePage = 1;
        }

        List<Group> joinGroups = groupService.findJoinGroups(userEmail, homePage);

        model.addAttribute("member", member);
        model.addAttribute("groups", joinGroups);

        return "joinGroups";
    }

    @GetMapping("/me/joinGroups/{id}")
    public String joinGroupsInfo(@PathVariable Long id, Model model, HttpServletRequest request) {
        String userEmail = getEmail(request);
        Member member = memberService.findByMemberByEmail(userEmail);
        Group group = groupService.findGroupById(id);
        List<Member> participants = group.getPeopleList();

        model.addAttribute("member", member);
        model.addAttribute("group", group);
        model.addAttribute("participants", participants);

        return "joinGroupInfo";
    }

    @DeleteMapping("/me/joinGroups/{id}")
    public String leaveGroup(@PathVariable Long id, HttpServletRequest request) {
        String userEmail = getEmail(request);
        Member member = memberService.findByMemberByEmail(userEmail);
        Group group = groupService.findGroupById(id);

        Notice notice = Notice.builder()
                .groupId(group.getId())
                .title("?????? ?????? ??????")
                .content(group.getTitle() + "??? ?????? ????????? ?????????????????????.")
                .member(member)
                .type(Type.GROUP_JOIN_CANCEL)
                .build();
        noticeRepository.save(notice);
        group.deletePeople(member);

        return "redirect:/me/joinGroups";
    }

    @GetMapping("/me/createGroups")
    public String gatherGroups(Model model, HttpServletRequest request,
                               @RequestParam(value = "page", required = false) Integer page) {
        String userEmail = getEmail(request);
        Member member = memberService.findByMemberByEmail(userEmail);

        Integer homePage = page;
        if (homePage == null) {
            homePage = 1;
        }

        List<Group> gatherGroups = groupService.findCreateGroups(userEmail, homePage);

        model.addAttribute("member", member);
        model.addAttribute("groups", gatherGroups);

        return "createGroups";
    }

    @GetMapping("/me/createGroups/{id}")
    public String gatherGroupsInfo(@PathVariable Long id, Model model, HttpServletRequest request) {
        String userEmail = getEmail(request);
        Member member = memberService.findByMemberByEmail(userEmail);
        Group group = groupService.findGroupById(id);
        List<Member> participants = group.getPeopleList();

        model.addAttribute("member", member);
        model.addAttribute("group", group);
        model.addAttribute("participants", participants);

        return "createGroupInfo";
    }

    @DeleteMapping("/me/createGroups/{id}")
    public String deleteGroup(@PathVariable Long id, HttpServletRequest request) {
        String userEmail = getEmail(request);
        Member member = memberService.findByMemberByEmail(userEmail);
        Group group = groupService.findGroupById(id);

        if (group.getHostMember().equals(member)) {
            Notice notice = Notice.builder()
                    .groupId(group.getId())
                    .title("????????? ?????????????????????.")
                    .content(group.getTitle() + "??? ????????? ?????????????????????.")
                    .member(group.getHostMember())
                    .type(Type.GROUP_GATHER_CANCEL)
                    .build();
            noticeRepository.save(notice);
            groupService.deleteGroup(id);
        }

        return "redirect:/me/createGroups";
    }


    @GetMapping("/me/notification")
    public String notice(Model model, HttpServletRequest request,
                         @RequestParam(value = "page", required = false) Integer page) {
        String userEmail = getEmail(request);
        Member member = memberService.findByMemberByEmail(userEmail);

        Integer noticePage = page;
        if (noticePage == null) {
            noticePage = 1;
        }
        List<Notice> notices = noticeService.getNotices(member, noticePage);

        model.addAttribute("member", member);
        model.addAttribute("notices", notices);

        return "notification";
    }

    @GetMapping("/me/notification/{id}")
    public String seeNotice(@PathVariable Long id, Model model, HttpServletRequest request) {
        String userEmail = getEmail(request);
        Member member = memberService.findByMemberByEmail(userEmail);
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new DefaultException(ErrorCode.INVALID_CHECK));
        Group group = groupRepository.forceFindById(notice.getGroupId()).orElseThrow(() -> new DefaultException(ErrorCode.INVALID_CHECK));

        model.addAttribute("notice", notice);
        model.addAttribute("member", member);
        model.addAttribute("group", group);
        model.addAttribute("participants", group.getPeopleList());

        return "noticeInfo";
    }

    @PostMapping("/me/notification/{id}")
    public String updateCheckNotice(@PathVariable Long id, HttpServletRequest request) {
        String userEmail = getEmail(request);
        Member member = memberService.findByMemberByEmail(userEmail);
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new DefaultException(ErrorCode.INVALID_CHECK));

        if (member.equals(notice.getMember())) {
            notice.updateCheck(1);
        }

        return "redirect:/me/notification";
    }

    @DeleteMapping("/me/notification/{id}")
    public String deleteNotice(@PathVariable Long id, HttpServletRequest request) {
        String userEmail = getEmail(request);
        Member member = memberService.findByMemberByEmail(userEmail);
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new DefaultException(ErrorCode.INVALID_OPTIONAL_ISPRESENT, "????????? ???????????????."));

        if (notice.getType().equals(Type.ANNOUNCEMENT)) {
            return "redirect:/me/notification";
        }
        notice.updateStatus(Status.DELETE);
        return "redirect:/me/notification";
    }

    @GetMapping("/home")
    public String mainForm(Model model, HttpServletRequest request,
                           @RequestParam(value = "category", required = false) String category,
                           @RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "search", required = false) String search) {
        String userEmail = getEmail(request);
        Member member = memberService.findByMemberByEmail(userEmail);

        log.info(member.getEmail());
        List<Group> groups;
        Integer homePage = page;
        if (homePage == null) {
            homePage = 1;
        }

        if (category != null) {
            if (category.equals("expireTime")) {
                groups = groupService.sortGroupsByDeadline(homePage);
            } else {
                groups = groupService.sortGroupByCategory(category, homePage);
            }
        } else if (search != null) {
            groups = groupService.findGroupsBySearch(search, homePage);
        } else {
            groups = groupService.findGroups(homePage);
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
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new DefaultException(ErrorCode.INVALID_CHECK, "???????????? ?????? ???????????????"));
        if (member.getStatus().equals(BAN)) {
            return "redirect:/home";
        }

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
    public String seeGroup(@PathVariable Long groupId, Model model, HttpServletRequest request) {
        String email = getEmail(request);
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new DefaultException(ErrorCode.INVALID_CHECK, "???????????? ?????? ???????????????."));
        Group group = groupService.findGroupById(groupId);
        List<Member> participants = group.getPeopleList();

        model.addAttribute("participants", participants);
        model.addAttribute("member", member);
        model.addAttribute("group", group);

        return "groupInfo";
    }

    @PostMapping("/group/{groupId}")
    public String joinGroup(HttpServletRequest request, @PathVariable String groupId) {
        String email = getEmail(request);
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new DefaultException(ErrorCode.INVALID_CHECK, "???????????? ?????? ???????????????."));
        Group group = groupService.findGroupById(Long.valueOf(groupId));

        if (member.getStatus().equals(BAN)) {
            return "redirect:/home";
        }

        if (group.getPeopleList().stream().anyMatch(people -> people.getEmail().equals(email))) {
            return "redirect:/home";
        }

        if (group.getMaxPeople() == group.getPeopleList().size()) {
            return "redirect:/home";
        }

        group.addPeople(member);
        Notice joinNotice = Notice.builder()
                .groupId(group.getId())
                .title("?????? ?????? ??????")
                .content(group.getTitle() + "??? ?????? ????????? ?????????????????????.")
                .member(member)
                .type(Type.GROUP_JOIN_SUCCESS)
                .build();
        noticeRepository.save(joinNotice);

        if (group.getPeopleList().size() == group.getMaxPeople()) {
            Notice notice = Notice.builder()
                    .groupId(group.getId())
                    .title("?????? ?????? ??????")
                    .content(group.getTitle() + "??? ?????? ????????? ?????????????????????")
                    .member(group.getHostMember())
                    .type(Type.GROUP_GATHER_SUCCESS)
                    .build();
            noticeRepository.save(notice);
        }

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
