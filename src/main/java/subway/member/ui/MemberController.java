package subway.member.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.auth.domain.AuthenticationPrincipal;
import subway.member.application.MemberService;
import subway.member.domain.LoginMember;
import subway.member.domain.Member;
import subway.member.dto.MemberRequest;
import subway.member.dto.MemberResponse;

import java.net.URI;

@RestController
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ResponseEntity createMember(@RequestBody MemberRequest request) {
        Member member = memberService.createMember(request.getEmail(), request.getPassword(), request.getAge());
        return ResponseEntity.created(URI.create("/members/" + member.getId())).build();
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponse> findMember(@PathVariable Long id) {
        Member member = memberService.findById(id);
        return ResponseEntity.ok().body(MemberResponse.of(member));
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable Long id,
                                                       @RequestBody MemberRequest request) {
        memberService.updateMember(id, request.getEmail(), request.getPassword(), request.getAge());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<MemberResponse> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/members/me")
    public ResponseEntity<MemberResponse> findMemberOfMine(@AuthenticationPrincipal LoginMember loginMember) {
        Member member = memberService.findById(loginMember.getId());
        return ResponseEntity.ok().body(MemberResponse.of(member));
    }

    @PutMapping("/members/me")
    public ResponseEntity<MemberResponse> updateMemberOfMine(@AuthenticationPrincipal LoginMember loginMember,
                                                             @RequestBody MemberRequest request) {
        memberService.updateMember(loginMember.getId(), request.getEmail(), request.getPassword(), request.getAge());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/members/me")
    public ResponseEntity<MemberResponse> deleteMemberOfMine(@AuthenticationPrincipal LoginMember loginMember) {
        memberService.deleteMember(loginMember.getId());
        return ResponseEntity.noContent().build();
    }
}
