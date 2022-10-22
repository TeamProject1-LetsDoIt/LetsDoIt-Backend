package teamproject1.letsdoit.member.domain.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import teamproject1.letsdoit.member.domain.Member;


//
//class BptMemberRepositoryTest {
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Test
//    public void bptTest() throws Exception {
//        //given
//        Member member1 = Member.builder()
//                .name("jin")
//                .email("jinpark99@naver.com")
//                .build();
//
//        Member member2 = Member.builder()
//                .name("ho")
//                .email("hojin99@naver.com")
//                .build();
//
//        Member member3 = Member.builder()
//                .name("se")
//                .email("se99@naver.com")
//                .build();
//
//        //when
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//        memberRepository.save(member3);
//
//        //then
//        Assertions.assertThat(memberRepository.findByEmail("jinpark99@naver.com").get().getEmail()).isEqualTo(member1.getEmail());
//        Assertions.assertThat(memberRepository.findByEmail("hojin99@naver.com").get().getEmail()).isEqualTo(member2.getEmail());
//        Assertions.assertThat(memberRepository.findByEmail("se99@naver.com").get().getEmail()).isEqualTo(member3.getEmail());
//    }
//
//    @Test
//    public void multipleTest() throws Exception {
//        //given
//        Member member1 = Member.builder()
//                .name("jin")
//                .email("jinpark99@naver.com")
//                .build();
//
//        Member member2 = Member.builder()
//                .name("ho")
//                .email("jinpark99@naver.com")
//                .build();
//
//        //when
//        memberRepository.save(member1);
//
//        //then
//    }
//}