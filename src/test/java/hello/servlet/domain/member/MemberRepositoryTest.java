package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach //테스트끝날때마다 초기화시키려고
    void afterEach(){ memberRepository.clearStore();}
    
    @Test
    void save() {
        //given
        Member member = new Member("hello", 20);

        //when
        Member savedMember = memberRepository.save(member);
        
        //then
        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findAll() {
        //given
        Member memberA = new Member("A", 20);
        Member memberB = new Member("B", 20);
        Member savedMemberA = memberRepository.save(memberA);
        Member savedMemberB = memberRepository.save(memberB);

        //when
        List<Member> allMembers = memberRepository.findAll();

        //then
        assertThat(allMembers.size()).isEqualTo(2);
        assertThat(allMembers).contains(memberA,memberB);
    }
    
}