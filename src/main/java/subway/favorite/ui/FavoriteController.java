package subway.favorite.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import subway.auth.infrastructure.AuthorizationExtractor;
import subway.auth.infrastructure.JwtTokenProvider;
import subway.favorite.application.FavoriteService;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class FavoriteController {

    private final FavoriteService favoriteService;


    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    // TODO: 즐겨찾기 기능 구현하기
    @PostMapping(value = "/favorites", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createFavorites(HttpServletRequest request, @RequestBody FavoriteRequest favoriteRequest){
        String token = AuthorizationExtractor.extract(request);
        favoriteService.save(token, favoriteRequest.getSource(), favoriteRequest.getTarget());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/favorites", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FavoriteResponse>> getFavorites(HttpServletRequest request){
        String token = AuthorizationExtractor.extract(request);
        List<Favorite> favorites = favoriteService.getFavorites(token);
        List<FavoriteResponse> favoriteResponses = favoriteService.convertFavoriteResponse(favorites);
        return ResponseEntity.ok().body(favoriteResponses);
    }

}
