package subway.auth.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import subway.auth.exception.InvalidTokenException;
import subway.auth.exception.LoginException;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.application.MemberService;
import subway.member.domain.LoginMember;
import subway.member.domain.Member;

@Service
public class AuthService {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthService(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(String email, String password) {
        try {
            Member member = memberService.findMemberByEmailAndPassword(email, password);
            return jwtTokenProvider.createToken(String.valueOf(member.getId()));
        } catch (EmptyResultDataAccessException e) {
            throw new LoginException();
        }
    }

    public LoginMember findMember(String accessToken) {
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new InvalidTokenException();
        }

        return LoginMember
                .of(memberService.findMemberById(Long.valueOf(jwtTokenProvider.getPayload(accessToken))));
    }

}
