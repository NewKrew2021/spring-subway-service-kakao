package subway.auth.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import subway.auth.application.AuthService;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.infrastructure.AuthorizationExtractor;
import subway.exception.LoginFailException;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    // TODO: 로그인(토큰 발급) 요청 처리하기

    @PostMapping(value = "/login/token", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> tryLogin(@RequestBody TokenRequest tokenRequest){
        try{
            TokenResponse tokenResponse  = authService.createToken(tokenRequest);
            return ResponseEntity.ok().body(tokenResponse);
        }
        catch (LoginFailException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
