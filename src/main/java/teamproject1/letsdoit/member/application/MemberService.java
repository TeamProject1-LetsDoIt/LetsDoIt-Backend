package teamproject1.letsdoit.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject1.letsdoit.common.dto.ApiResponse;
import teamproject1.letsdoit.common.exception.advice.assertThat.DefaultAssert;
import teamproject1.letsdoit.member.domain.Member;
import teamproject1.letsdoit.member.domain.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public ResponseEntity<?> me (String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        DefaultAssert.isOptionalPresent(member);

        ApiResponse apiResponse = ApiResponse.builder().check(true).information(member.get()).build();
        return ResponseEntity.ok(apiResponse);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

}
