package subway.member.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.auth.exception.LoginException;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;
import subway.member.dto.MemberRequest;
import subway.member.dto.MemberResponse;

@Service
public class MemberService {

    private final MemberDao memberDao;

    @Autowired
    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional
    public Member createMember(MemberRequest request) {
        return memberDao.insert(request.toMember());
    }

    public Member findMemberById(Long id) {
        return memberDao.findById(id);
    }

    public Member findMemberByEmailAndPassword(String email, String password) {
        try {
            return memberDao.findByEmailAndPassword(email, password);
        } catch (EmptyResultDataAccessException exception) {
            throw new LoginException();
        }
    }

    @Transactional
    public void updateMember(Long id, MemberRequest memberRequest) {
        memberDao.update(new Member(id, memberRequest.getEmail(), memberRequest.getPassword(),
                memberRequest.getAge()));
    }

    @Transactional
    public void deleteMember(Long id) {
        memberDao.deleteById(id);
    }
}
