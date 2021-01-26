package subway.favorite.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.auth.domain.AuthenticationPrincipal;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.favorite.application.FavoriteService;
import subway.member.domain.LoginMember;

import java.net.URI;


@RestController
@RequestMapping("/favorites")
public class FavoriteController {
    // TODO: 즐겨찾기 기능 구현하기
    private FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createFavorite(@AuthenticationPrincipal LoginMember loginMember,
                                         @RequestBody FavoriteRequest favoriteRequest) {
        FavoriteResponse favoriteResponse = favoriteService.createFavorite(loginMember.getId(),
                favoriteRequest.getSource(),
                favoriteRequest.getTarget());

        return ResponseEntity.created(URI.create("/favorites/" + favoriteResponse.getId())).body(favoriteResponse);
    }

    @GetMapping()
    public ResponseEntity getFavorite(@AuthenticationPrincipal LoginMember loginMember){
        return ResponseEntity.ok(favoriteService.getFavorite(loginMember.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFavorite(@PathVariable Long id){
        favoriteService.deleteFavorite(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
