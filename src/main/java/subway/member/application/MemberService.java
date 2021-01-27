package subway.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.common.domain.Age;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;
import subway.member.dto.MemberRequest;
import subway.member.dto.MemberResponse;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional
    public MemberResponse createMember(MemberRequest request) {
        Member member = memberDao.insert(Member.of(request.getEmail(), request.getPassword(), Age.from(request.getAge())));
        return MemberResponse.from(member);
    }

    public MemberResponse findMember(Long id) {
        Member member = memberDao.findById(id);
        return MemberResponse.from(member);
    }

    @Transactional
    public void updateMember(Long id, MemberRequest request) {
        memberDao.update(Member.of(id, request.getEmail(), request.getPassword(), Age.from(request.getAge())));
    }

    @Transactional
    public void deleteMember(Long id) {
        memberDao.deleteById(id);
    }

    public boolean isInValidMember(String email, String password) {
        return memberDao.isNotExistsMemberByEmailAndPassword(email, password);
    }

    public Member findMemberByEmail(String email) {
        return memberDao.findByEmail(email);
    }
}
