package subway.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.exception.UnAuthorizedException;
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
    private MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
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

    @Transactional
    public void updateMember(Long id, MemberRequest memberRequest) {
        memberDao.update(new Member(id, memberRequest.getEmail(), memberRequest.getPassword(), memberRequest.getAge()));
    }

    @Transactional
    public void deleteMember(Long id) {
        memberDao.deleteById(id);
    }

    public LoginMember login(String email, String password) {
        Member member = memberDao.findByEmail(email).orElseThrow(UnAuthorizedException::new);
        if (!member.getPassword().equals(password)) {
            throw new UnAuthorizedException("Email or Password not correct");
        }
        return new LoginMember(member.getId(), member.getEmail(), member.getAge());
    }
}
