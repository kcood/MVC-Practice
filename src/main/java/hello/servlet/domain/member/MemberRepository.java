package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {

    //static -> MemberRepository가 new로 여러개 생성돼도 얘들은 딱 하나씩만 생성됨
    //밑에 싱글톤으로 만들어서 static 없애도 되지만 일단 그냥 둘게요
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    //싱글톤으로 만들기
    private static final MemberRepository instance = new MemberRepository();

    //싱글톤이니까 생성자 막기
    private MemberRepository(){}

    public static MemberRepository getInstance(){
        return instance;
    }

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
