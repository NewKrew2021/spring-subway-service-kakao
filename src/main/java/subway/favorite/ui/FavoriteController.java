package subway.favorite.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import subway.favorite.application.FavoriteService;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;

import java.net.URI;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {
    // TODO: 즐겨찾기 기능 구현하기

    private FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity<FavoriteResponse> createFavorite(@RequestBody FavoriteRequest favoriteRequest){
        FavoriteResponse favoriteResponse = favoriteService.createFavorite(favoriteRequest);
        return ResponseEntity
                .created(URI.create("/favorites/" + favoriteResponse.getId()))
                .body(favoriteResponse);
    }

}
