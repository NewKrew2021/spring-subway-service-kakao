package subway.member.application;

import org.springframework.stereotype.Service;
import subway.auth.application.AuthorizationException;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;
import subway.member.dto.MemberResponse;

@Service
public class MemberService {
    private MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Member createMember(String email, String password, Integer age) {
        return memberDao.insert(new Member(email, password, age));
    }

    public Member findById(Long id) {
        return memberDao.findById(id);
    }

    public void validateMember(String email, String password) {
        Member member = memberDao.findByEmail(email);

        if (!password.equals(member.getPassword())) {
            throw new AuthorizationException();
        }
    }

    public MemberResponse findByEmail(String email) {
        Member member = memberDao.findByEmail(email);
        return MemberResponse.of(member);
    }

    public void updateMember(Long id, String email, String password, Integer age) {
        memberDao.update(new Member(id, email, password, age));
    }

    public void deleteMember(Long id) {
        memberDao.deleteById(id);
    }
}
