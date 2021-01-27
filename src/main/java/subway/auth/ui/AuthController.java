package subway.auth.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.service.AuthService;
import subway.member.application.MemberService;

@RestController
public class AuthController {

    public static final String BAD_REQUEST_MESSAGE = "부적절한 요청입니다.";
    public static final String USER_NOT_EXIST_MESSAGE = "존재하지 않는 유저입니다.";
    private final AuthService authService;
    private final MemberService memberService;

    public AuthController(AuthService authService, MemberService memberService) {
        this.authService = authService;
        this.memberService = memberService;
    }

    @PostMapping("/login/token")
    public ResponseEntity tokenLogin(@RequestBody TokenRequest tokenRequest) {
        if (!tokenRequest.isValidRequest()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BAD_REQUEST_MESSAGE);
        }

        if (!memberService.isPossibleLogin(tokenRequest)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(USER_NOT_EXIST_MESSAGE);
        }

        TokenResponse tokenResponse = authService.createToken(tokenRequest);
        return ResponseEntity.ok().body(tokenResponse);
    }
}
