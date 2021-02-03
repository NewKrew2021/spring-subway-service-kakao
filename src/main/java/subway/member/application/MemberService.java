package subway.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.auth.application.AuthService;
import subway.exception.InvalidTokenException;
import subway.line.domain.Line;
import subway.line.dto.LineRequest;
import subway.member.dao.MemberDao;
import subway.member.domain.LoginMember;
import subway.member.domain.Member;
import subway.member.dto.MemberRequest;
import subway.member.dto.MemberResponse;

import java.util.Optional;

@Service
public class MemberService {
    private AuthService authService;
    private MemberDao memberDao;

    public MemberService(AuthService authService, MemberDao memberDao) {
        this.authService = authService;
        this.memberDao = memberDao;
    }

    @Transactional
    public MemberResponse createMember(MemberRequest request) {
        Member member = memberDao.insert(request.toMember());
        return MemberResponse.of(member);
    }

    public MemberResponse findMember(Long id) {
        Member member = memberDao.findById(id);
        return MemberResponse.of(member);
    }

    public Member getMemberByToken(String token){
        authService.validateToken(token);
        Member member = authService.getLoginMember(token);
        if(member == null){
            member = memberDao.findByEmail(authService.getPayLoad(token));
            authService.putLoginMember(token, member);
        }
        return member;
    }

    public MemberResponse findMemberByEmail(String email){
        Member member = memberDao.findByEmail(email);
        return MemberResponse.of(member);
    }

    @Transactional
    public void updateMember(Long id, MemberRequest memberRequest) {
        memberDao.update(new Member(id, memberRequest.getEmail(), memberRequest.getPassword(), memberRequest.getAge()));
    }

    @Transactional
    public void deleteMember(Long id) {
        memberDao.deleteById(id);
    }
}
