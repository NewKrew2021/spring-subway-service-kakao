package subway.favorite.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.auth.infrastructure.AuthorizationExtractor;
import subway.exception.InvalidTokenException;
import subway.favorite.application.FavoriteService;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
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
        Favorite favorite = favoriteService.save(token, favoriteRequest.getSource(), favoriteRequest.getTarget());
        return ResponseEntity.created(URI.create("/favorites/" + favorite.getId())).build();
    }

    @GetMapping(value = "/favorites", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FavoriteResponse>> getFavorites(HttpServletRequest request){
        String token = AuthorizationExtractor.extract(request);
        List<Favorite> favorites = favoriteService.getFavorites(token);
        List<FavoriteResponse> favoriteResponses = favoriteService.convertFavoriteResponse(favorites);
        return ResponseEntity.ok().body(favoriteResponses);
    }

    @DeleteMapping(value = "/favorites/{favoriteId}")
    public ResponseEntity<Void> deleteFavorites(HttpServletRequest request, @PathVariable("favoriteId") Long favoriteId){
        String token = AuthorizationExtractor.extract(request);
        favoriteService.deleteFavorites(token, favoriteId);
        return ResponseEntity.noContent().build();
    }
}
