package subway.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.exception.InvalidRequestException;
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
        Member member = memberDao.insert(request.toMember());
        return MemberResponse.of(member);
    }

    public MemberResponse findMember(Long id) {
        Member member = memberDao.findById(id);
        return MemberResponse.of(member);
    }

    public MemberResponse findMemberByEmail(String email) {
        Member member = memberDao.findByEmail(email);
        return MemberResponse.of(member);
    }

    @Transactional
    public void updateMember(Long id, MemberRequest memberRequest) {
        Member newMember = new Member(id, memberRequest.getEmail(), memberRequest.getPassword(), memberRequest.getAge());
        if (memberDao.update(newMember) == 0) {
            throw new InvalidRequestException("회원 정보 수정에 실패하였습니다.");
        }
    }

    @Transactional
    public void deleteMember(Long id) {
        if (memberDao.deleteById(id) == 0) {
            throw new InvalidRequestException("회원 정보 삭제에 실패하였습니다.");
        }
    }
}
