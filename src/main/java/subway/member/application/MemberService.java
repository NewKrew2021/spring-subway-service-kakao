package subway.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.exception.InvalidMemberException;
import subway.line.domain.Line;
import subway.line.dto.LineRequest;
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

    @Transactional
    public Member createMember(MemberRequest request) {
        return memberDao.insert(request.toMember());
    }

    public Member findMember(Long id) {
        return memberDao.findById(id);
    }

    public Member findByEmail(String email){
        return memberDao.findByEmail(email)
                .filter(member -> member.hasSameEmail(email))
                .orElseThrow(InvalidMemberException::new);
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
