package subway.member.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.auth.exception.NoSuchEmailException;
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
    public MemberResponse createMember(MemberRequest request) {
        Member member = memberDao.insert(request.toMember());
        return MemberResponse.of(member);
    }

    public MemberResponse findMemberById(Long id) {
        Member member = memberDao.findById(id);
        return MemberResponse.of(member);
    }

    public Member findMemberByEmail(String email) {
        try {
            return memberDao.findByEmail(email);
        } catch (EmptyResultDataAccessException exception) {
            throw new NoSuchEmailException();
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
