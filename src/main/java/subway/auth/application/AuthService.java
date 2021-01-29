package subway.auth.application;

import org.springframework.stereotype.Service;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exception.InvalidMemberException;
import subway.exception.InvalidTokenException;
import subway.member.application.MemberService;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;

@Service
public class AuthService {
    private JwtTokenProvider jwtTokenProvider;
    private MemberService memberService;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    public String createAuth(String email, String password) {
        if(!memberService.findByEmail(email).getPassword().equals(password)){
            throw new InvalidMemberException();
        }
        return jwtTokenProvider.createToken(email);
    }

    public Member getMemberByToken(String token) {
        if(jwtTokenProvider.validateToken(token)) {
            String payLoad = jwtTokenProvider.getPayload(token);
            Member member = memberService.findByEmail(payLoad);
            return member;
        }
        throw new InvalidTokenException("유효한 토큰이 아닙니다.");
    }
}


