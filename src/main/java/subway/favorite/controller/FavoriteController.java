package subway.favorite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.auth.domain.AuthenticationPrincipal;
import subway.favorite.dto.FavoriteRequest;
import subway.favorite.dto.FavoriteResponse;
import subway.favorite.service.FavoriteService;
import subway.member.domain.LoginMember;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {
    // TODO: 즐겨찾기 기능 구현하기

    private FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity<FavoriteResponse> createFavorite(@AuthenticationPrincipal LoginMember loginMember,
                                                           @RequestBody FavoriteRequest favoriteRequest) {
        FavoriteResponse favoriteResponse = favoriteService.createFavorite(favoriteRequest, loginMember.getId());
        return ResponseEntity
                .created(URI.create("/favorites/" + favoriteResponse.getId()))
                .body(favoriteResponse);
    }

    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> getFavorites(@AuthenticationPrincipal LoginMember loginMember) {
        List<FavoriteResponse> favorites = favoriteService.findAllByUserId(loginMember.getId()).stream()
                .map(favorite -> FavoriteResponse.of(favorite))
                .collect(Collectors.toList());
        return ResponseEntity
                .ok()
                .body(favorites);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FavoriteResponse> deleteFavorite(@AuthenticationPrincipal LoginMember loginMember,
                                                           @PathVariable Long id) {
        favoriteService.deleteFavorite(loginMember, id);
        return ResponseEntity
                .noContent().build();
    }
}
