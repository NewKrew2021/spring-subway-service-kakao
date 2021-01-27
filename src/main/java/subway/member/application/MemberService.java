package subway.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.auth.application.AuthorizationException;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;
import subway.member.dto.MemberRequest;
import subway.member.dto.MemberResponse;

@Service
public class MemberService {
    private MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public MemberResponse createMember(MemberRequest request) {
        Member member = memberDao.insert(request.toMember());
        return MemberResponse.of(member);
    }

    public MemberResponse findById(Long id) {
        Member member = memberDao.findById(id);
        return MemberResponse.of(member);
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

    public void updateMember(Long id, MemberRequest memberRequest) {
        memberDao.update(new Member(id, memberRequest.getEmail(), memberRequest.getPassword(), memberRequest.getAge()));
    }

    public void deleteMember(Long id) {
        memberDao.deleteById(id);
    }
}
