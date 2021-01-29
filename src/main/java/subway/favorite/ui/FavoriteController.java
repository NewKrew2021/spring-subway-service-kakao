package subway.favorite.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.auth.domain.AuthenticationPrincipal;
import subway.auth.infrastructure.AuthorizationExtractor;
import subway.exception.InvalidTokenException;
import subway.favorite.application.FavoriteService;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.member.domain.LoginMember;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
public class FavoriteController {

    private final FavoriteService favoriteService;


    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping(value = "/favorites", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createFavorites(@AuthenticationPrincipal LoginMember loginMember, @RequestBody FavoriteRequest favoriteRequest){
        Favorite favorite = favoriteService.save(loginMember.getId(), favoriteRequest.getSource(), favoriteRequest.getTarget());
        return ResponseEntity.created(URI.create("/favorites/" + favorite.getId())).build();
    }

    @GetMapping(value = "/favorites", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FavoriteResponse>> getFavorites(@AuthenticationPrincipal LoginMember loginMember){
        List<Favorite> favorites = favoriteService.getFavorites(loginMember.getEmail());
        List<FavoriteResponse> favoriteResponses = favoriteService.convertFavoriteResponse(favorites);
        return ResponseEntity.ok().body(favoriteResponses);
    }

    @DeleteMapping(value = "/favorites/{favoriteId}")
    public ResponseEntity<Void> deleteFavorites(@AuthenticationPrincipal LoginMember loginMember, @PathVariable("favoriteId") Long favoriteId){
        favoriteService.deleteFavorites(loginMember.getEmail(), favoriteId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Void> invalidToken(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
