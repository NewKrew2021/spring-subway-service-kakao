package subway.favorite.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.auth.domain.AuthenticationPrincipal;
import subway.favorite.application.FavoriteService;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.member.domain.LoginMember;

import java.net.URI;

@RestController
public class FavoriteController {
    // TODO: 즐겨찾기 기능 구현하기

    private FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/favorites")
    public ResponseEntity creatFavorites(@AuthenticationPrincipal LoginMember loginMember, @RequestBody FavoriteRequest favoriteRequest) {
        favoriteService.insertFavorite(favoriteRequest, loginMember.getId());
        return ResponseEntity.created(URI.create("/favorite/" + loginMember.getId())).build();
    }

    @GetMapping("/favorites")
    public ResponseEntity showFavorites(@AuthenticationPrincipal LoginMember loginMember) {
        FavoriteResponse favoriteResponse = favoriteService.showFavoriteByMemberId(loginMember.getId());
        return ResponseEntity.ok().body(favoriteResponse);
    }

    @DeleteMapping("/favorite/{favoriteId}")
    public ResponseEntity deleteFavorite(@AuthenticationPrincipal LoginMember loginMember, @PathVariable Long favoriteId) {
        favoriteService.deleteFavoriteById(loginMember.getId(), favoriteId);
        return ResponseEntity.noContent().build();
    }

}
