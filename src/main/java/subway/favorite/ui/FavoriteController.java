package subway.favorite.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.auth.domain.AuthenticationPrincipal;
import subway.favorite.application.FavoriteService;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.member.domain.LoginMember;
import subway.station.application.StationService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {
    FavoriteService favoriteService;
    StationService stationService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService, StationService stationService) {
        this.favoriteService = favoriteService;
        this.stationService = stationService;
    }

    @PostMapping
    public ResponseEntity<FavoriteResponse> createFavorite(@AuthenticationPrincipal LoginMember loginMember, @RequestBody FavoriteRequest favoriteRequest) {
        Favorite favorite = favoriteService.createFavorite(loginMember.getId(), favoriteRequest);
        FavoriteResponse favoriteResponse = FavoriteResponse.of(favorite);
        return ResponseEntity.created(URI.create("/favorites/" + favoriteResponse.getId())).body(favoriteResponse);
    }

    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> getFavorites(@AuthenticationPrincipal LoginMember loginMember) {
        List<FavoriteResponse> favoriteResponses = favoriteService.findFavoritesByMemberId(loginMember.getId())
                .stream()
                .map(FavoriteResponse::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(favoriteResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFavorite(@AuthenticationPrincipal LoginMember loginMember, @PathVariable Long id) {
        favoriteService.deleteFavorite(id);
        return ResponseEntity.noContent().build();
    }
}
