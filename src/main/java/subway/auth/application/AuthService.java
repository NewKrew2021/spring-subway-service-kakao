package subway.auth.application;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.stereotype.Service;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.exception.UnAuthorizedException;
import subway.member.application.MemberService;
import subway.member.domain.LoginMember;

@Service
public class AuthService {
    private JwtTokenProvider tokenProvider;
    private MemberService memberService;

    public AuthService(JwtTokenProvider tokenProvider, MemberService memberService) {
        this.tokenProvider = tokenProvider;
        this.memberService = memberService;
    }

    public TokenResponse createTokenFor(LoginMember loginMember) {
        String token = tokenProvider.createToken(loginMember.getEmail());
        return new TokenResponse(token);
    }

    public LoginMember validateToken(String token) {
        try{
            String email = tokenProvider.getPayload(token);
            return memberService.findLoginMember(email);
        } catch (MalformedJwtException e) {
            throw new UnAuthorizedException();
        }
    }
}