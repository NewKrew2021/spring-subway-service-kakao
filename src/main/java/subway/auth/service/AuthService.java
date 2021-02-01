package subway.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import subway.auth.dto.TokenRequest;
import subway.auth.dto.TokenResponse;
import subway.auth.exceptions.UnauthenticatedException;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;

@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    @Autowired
    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public TokenResponse createToken(TokenRequest request) {
        try {
            Member member = memberDao.findByEmailPassword(request.getEmail(), request.getPassword());
            return new TokenResponse(jwtTokenProvider.createToken(String.valueOf(member.getId())));
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UnauthenticatedException("존재하지 않는 사용자입니다");
        }
    }

}
