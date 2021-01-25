package subway.auth.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.auth.application.AuthService;
import subway.auth.domain.AuthenticationPrincipal;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.exception.LoginFailException;
import subway.member.application.MemberService;
import subway.member.domain.LoginMember;
import subway.member.dto.MemberRequest;
import subway.member.dto.MemberResponse;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> login(@RequestBody TokenRequest tokenRequest) {
        String accessToken;
        try {
            accessToken = authService.login(tokenRequest);
        } catch (Exception e) {
            throw new LoginFailException();
        }
        return ResponseEntity.ok().body(new TokenResponse(accessToken));
    }
}
