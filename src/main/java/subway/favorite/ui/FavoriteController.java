package subway.favorite.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.auth.domain.AuthenticationPrincipal;
import subway.exception.BadMemberException;
import subway.favorite.application.FavoriteService;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.member.domain.LoginMember;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/favorites")
    public ResponseEntity createFavorite(@AuthenticationPrincipal Optional<LoginMember> loginMemberOptional, @RequestBody FavoriteRequest favoriteRequest) {
        Favorite favorite = favoriteService.saveFavorite(
                loginMemberOptional.orElseThrow(BadMemberException::new), favoriteRequest);
        return ResponseEntity.created(URI.create("/favorites/" + favorite.getId())).build();
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<FavoriteResponse>> findFavorites(@AuthenticationPrincipal Optional<LoginMember> loginMemberOptional){
        return ResponseEntity.ok().body(favoriteService.findAllFavorites(
                loginMemberOptional.orElseThrow(BadMemberException::new)));
    }

    @DeleteMapping("/favorites/{id}")
    public ResponseEntity removeFavorite(@AuthenticationPrincipal Optional<LoginMember> loginMemberOptional, @PathVariable Long id){
        favoriteService.deleteFavorite(
                loginMemberOptional.orElseThrow(BadMemberException::new), id);
        return ResponseEntity.noContent().build();
    }
}
