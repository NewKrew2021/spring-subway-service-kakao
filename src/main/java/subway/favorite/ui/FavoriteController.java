package subway.favorite.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.auth.domain.AuthenticationPrincipal;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.favorite.exceptions.FavoriteNullRequestException;
import subway.favorite.service.FavoriteService;
import subway.member.domain.LoginMember;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity<Void> createFavorite(@AuthenticationPrincipal LoginMember loginMember,
                                               @RequestBody FavoriteRequest favoriteRequest) {
        if(favoriteRequest == null){
            throw new FavoriteNullRequestException("Favorite 값이 null 입니다");
        }
        long favoriteId = favoriteService.insert(new Favorite(loginMember.getId(), favoriteRequest.getSource(), favoriteRequest.getTarget()));
        return ResponseEntity.created(URI.create("/favorites/" + favoriteId)).build();
    }

    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> getFavorites(@AuthenticationPrincipal LoginMember loginMember) {
        return ResponseEntity.ok().body(favoriteService.findFavoriteResponses(loginMember.getId()));
    }

    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<Void> deleteFavorite(@AuthenticationPrincipal LoginMember loginMember,
                                               @PathVariable long favoriteId) {
        favoriteService.delete(loginMember.getId(), favoriteId);
        return ResponseEntity.noContent().build();
    }
}
