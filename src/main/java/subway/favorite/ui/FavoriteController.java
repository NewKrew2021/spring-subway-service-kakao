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
@RequestMapping("/favorites")
public class FavoriteController {
    private FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity createFavorite(@AuthenticationPrincipal LoginMember loginMember,
                                         @RequestBody FavoriteRequest request) {
        FavoriteResponse response = favoriteService.createFavorite(loginMember.getId(), request);
        return ResponseEntity.created(URI.create("/favorites/" + response.getId())).build();
    }

    @GetMapping
    public ResponseEntity findFavorite(@AuthenticationPrincipal LoginMember loginMember) {
        return ResponseEntity.ok().body(favoriteService.findByMemberId(loginMember.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFavorite(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long id) {
        favoriteService.deleteFavoriteById(id);

        return ResponseEntity.noContent().build();
    }
}