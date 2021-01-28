package subway.member.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import subway.member.domain.Member;

public class MemberResponse {
    private final Long id;
    private final String email;
    private final Integer age;

    @JsonCreator
    public MemberResponse(Long id, String email, Integer age) {
        this.id = id;
        this.email = email;
        this.age = age;
    }

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getId(), member.getEmail(), member.getAge());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }
}
