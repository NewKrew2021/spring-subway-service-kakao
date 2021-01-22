package subway.auth.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import subway.auth.application.AuthService;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;

@RestController
public class AuthController {
    // TODO: 로그인(토큰 발급) 요청 처리하기
    AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login/token")
    public ResponseEntity createToken(@RequestBody TokenRequest request) {
        TokenResponse tokenResponse = authService.createToken(request);
        return ResponseEntity.ok().body(tokenResponse);
    }

}



//    POST /login/token HTTP/1.1
//    content-type: application/json; charset=UTF-8
//    accept: application/json
//    {
//        "password": "password",
//            "email": "email@email.com"
//    }

//    HTTP/1.1 200
//    Content-Type: application/json
//    Transfer-Encoding: chunked
//    Date: Sun, 27 Dec 2020 04:32:26 GMT
//    Keep-Alive: timeout=60
//    Connection: keep-alive
//
//    {
//        "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJpYXQiOjE2MDkwNDM1NDYsImV4cCI6MTYwOTA0NzE0Nn0.dwBfYOzG_4MXj48Zn5Nmc3FjB0OuVYyNzGqFLu52syY"
//    }
