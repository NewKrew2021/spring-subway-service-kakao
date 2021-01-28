package subway.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.exception.InvalidMemberException;
import subway.member.dao.MemberDao;
import subway.member.domain.Member;
import subway.member.dto.MemberResponse;

import java.util.Optional;

@Service
public class MemberService {
    private MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional
    public MemberResponse createMember(String email, String password, int age) {
        Member member = memberDao.insert(new Member(email, password, age));
        return MemberResponse.of(member);
    }

    public MemberResponse findMember(Long id) {
        Optional<Member> member = memberDao.findById(id);
        if (!member.isPresent()) {
            throw new InvalidMemberException();
        }
        return MemberResponse.of(member.get());
    }

    @Transactional
    public void updateMember(Long id, String email, String password, int age) {
        memberDao.update(new Member(id, email, password, age));
    }

    @Transactional
    public void deleteMember(Long id) {
        memberDao.deleteById(id);
    }

}
