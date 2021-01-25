package subway.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import subway.auth.application.AuthService;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.member.application.MemberService;
import subway.member.domain.LoginMember;

@RestController
public class AuthController {
    MemberService memberService;
    AuthService authService;

    public AuthController(MemberService memberService, AuthService authService) {
        this.memberService = memberService;
        this.authService = authService;
    }

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> login(@RequestBody TokenRequest request) {
        LoginMember loginMember = memberService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(authService.createTokenFor(loginMember));
    }
}
