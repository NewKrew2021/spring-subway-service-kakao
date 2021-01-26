package subway.auth.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import subway.auth.application.AuthService;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.exception.InvalidMemberException;
import subway.auth.infrastructure.JwtTokenProvider;

@RestController
public class AuthController {
    private AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> login(@RequestBody TokenRequest tokenRequest) {
        String email = tokenRequest.getEmail();
        String password = tokenRequest.getPassword();
        if (authService.checkInvalidMember(email, password)) {
            throw new InvalidMemberException("로그인 정보가 맞지 않습니다.");
        }
        return ResponseEntity.ok().body(new TokenResponse(jwtTokenProvider.createToken(email)));
    }

    @ExceptionHandler(InvalidMemberException.class)
    public ResponseEntity<Void> invalidToken() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
