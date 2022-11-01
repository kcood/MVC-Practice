package hello.servlet.domain.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private Long id;
    private String username;
    private int age;

    //자바 빈 규약을 지키기위해 기본 생성자도 추가. 없어도 동작은 되는듯?
    // https://dololak.tistory.com/133
    public Member() {
    }

    public Member(String username, int age){
        this.username = username;
        this.age = age;
    }

}
