package subway.favorite.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.auth.domain.AuthenticationPrincipal;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.favorite.service.FavoriteService;
import subway.member.domain.LoginMember;

import java.net.URI;

@RestController
public class FavoriteController {
    public static final String LOGIN_REQUIRED_MESSAGE = "로그인이 필요한 서비스입니다.";

    private FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/favorites")
    public ResponseEntity creatFavorites(@AuthenticationPrincipal LoginMember loginMember, @RequestBody FavoriteRequest favoriteRequest) {
        if (!loginMember.isValid()) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(LOGIN_REQUIRED_MESSAGE);
        }
        favoriteService.insertFavorite(favoriteRequest, loginMember.getId());
        return ResponseEntity.created(URI.create("/favorite/" + loginMember.getId())).build();
    }

    @GetMapping("/favorites")
    public ResponseEntity findFavorites(@AuthenticationPrincipal LoginMember loginMember) {
        if (!loginMember.isValid()) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(LOGIN_REQUIRED_MESSAGE);
        }
        FavoriteResponse favoriteResponse = favoriteService.showFavoriteByMemberId(loginMember.getId());
        return ResponseEntity.ok().body(favoriteResponse);
    }

    @DeleteMapping("/favorite/{favoriteId}")
    public ResponseEntity deleteFavorite(@AuthenticationPrincipal LoginMember loginMember, @PathVariable Long favoriteId) {
        if (!loginMember.isValid()) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(LOGIN_REQUIRED_MESSAGE);
        }
        favoriteService.deleteFavoriteById(loginMember.getId(), favoriteId);
        return ResponseEntity.noContent().build();
    }

}
