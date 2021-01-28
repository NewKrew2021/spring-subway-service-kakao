package subway.member;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import subway.AcceptanceTest;
import subway.auth.dto.TokenResponse;
import subway.member.dto.MemberRequest;
import subway.member.dto.MemberResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static subway.auth.AuthAcceptanceTest.로그인되어_있음;

public class MemberAcceptanceTest extends AcceptanceTest {
    public static final String EMAIL = "email@email.com";
    public static final String PASSWORD = "password";
    public static final int AGE = 20;
    public static final String NEW_EMAIL = "new_email@email.com";
    public static final String NEW_PASSWORD = "new_password";
    public static final int NEW_AGE = 30;
    public static final String ADMIN_EMAIL = "admin";


    @DisplayName("내 회원 정보를 관리한다.")
    @Test
    void manageMyInfo() {
        ExtractableResponse<Response> createResponse = 회원_생성을_요청(EMAIL, PASSWORD, AGE);
        회원_생성됨(createResponse);

        TokenResponse 사용자 = 로그인되어_있음(EMAIL, PASSWORD);

        ExtractableResponse<Response> findResponse = 내_회원_정보_조회_요청(사용자);
        회원_정보_조회됨(findResponse, EMAIL, AGE);

        ExtractableResponse<Response> updateResponse = 내_회원_정보_수정_요청(사용자, EMAIL, NEW_PASSWORD, NEW_AGE);
        회원_정보_수정됨(updateResponse);

        ExtractableResponse<Response> deleteResponse = 내_회원_삭제_요청(사용자);
        회원_삭제됨(deleteResponse);
    }

    @DisplayName("관리자가 회원 정보를 관리한다.")
    @Test
    void manageMember() {
        ExtractableResponse<Response> createResponse1 = 회원_생성을_요청(ADMIN_EMAIL, PASSWORD, AGE);
        ExtractableResponse<Response> createResponse2 = 회원_생성을_요청("other@email.com", PASSWORD, 35);
        회원_생성됨(createResponse1);
        회원_생성됨(createResponse2);

        TokenResponse 관리자 = 로그인되어_있음(ADMIN_EMAIL, PASSWORD);
        TokenResponse 사용자 = 로그인되어_있음("other@email.com", PASSWORD);

        ExtractableResponse<Response> findMineResponse = 내_회원_정보_조회_요청(사용자);
        MemberResponse 사용자_정보 = findMineResponse.as(MemberResponse.class);

        ExtractableResponse<Response> findResponse = 회원_정보_조회_요청(관리자, 사용자_정보.getId());
        회원_정보_조회됨(findResponse, "other@email.com", 35);

        ExtractableResponse<Response> updateResponse = 회원_정보_수정_요청(관리자, 사용자_정보.getId(), EMAIL, NEW_PASSWORD, NEW_AGE);
        회원_정보_수정됨(updateResponse);

        ExtractableResponse<Response> deleteResponse = 회원_삭제_요청(관리자, 사용자_정보.getId());
        회원_삭제됨(deleteResponse);
    }

    @DisplayName("관리자가 아니면 회원 정보를 관리하지 못한다.")
    @Test
    void userCanNotManageMember() {
        ExtractableResponse<Response> createResponse1 = 회원_생성을_요청(EMAIL, PASSWORD, AGE);
        ExtractableResponse<Response> createResponse2 = 회원_생성을_요청("other@email.com", "password", 35);
        회원_생성됨(createResponse1);
        회원_생성됨(createResponse2);

        TokenResponse 사용자1 = 로그인되어_있음(EMAIL, PASSWORD);
        TokenResponse 사용자2 = 로그인되어_있음("other@email.com", PASSWORD);

        ExtractableResponse<Response> findMineResponse = 내_회원_정보_조회_요청(사용자2);
        MemberResponse 사용자2_정보 = findMineResponse.as(MemberResponse.class);

        ExtractableResponse<Response> findResponse = 회원_정보_조회_요청(사용자1, 사용자2_정보.getId());
        권한이_없음(findResponse);

        ExtractableResponse<Response> updateResponse = 회원_정보_수정_요청(사용자1, 사용자2_정보.getId(), EMAIL, NEW_PASSWORD, NEW_AGE);
        권한이_없음(updateResponse);

        ExtractableResponse<Response> deleteResponse = 회원_삭제_요청(사용자1, 사용자2_정보.getId());
        권한이_없음(deleteResponse);
    }


    public static ExtractableResponse<Response> 회원_생성을_요청(String email, String password, Integer age) {
        MemberRequest memberRequest = new MemberRequest(email, password, age);

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 내_회원_정보_조회_요청(TokenResponse tokenResponse) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    public static ExtractableResponse<Response> 내_회원_정보_수정_요청(TokenResponse tokenResponse, String email, String password, Integer age) {
        MemberRequest memberRequest = new MemberRequest(email, password, age);

        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().put("/members/me")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 내_회원_삭제_요청(TokenResponse tokenResponse) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .when().delete("/members/me")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원_정보_조회_요청(TokenResponse tokenResponse, Long id) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/admin/{id}", id)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원_정보_수정_요청(TokenResponse tokenResponse, Long id, String email, String password, Integer age) {
        MemberRequest memberRequest = new MemberRequest(email, password, age);

        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .when().put("/members/admin/{id}", id)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원_삭제_요청(TokenResponse tokenResponse, Long id) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .when().delete("/members/admin/{id}", id)
                .then().log().all()
                .extract();
    }

    public static void 회원_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static void 회원_정보_조회됨(ExtractableResponse<Response> response, String email, int age) {
        MemberResponse memberResponse = response.as(MemberResponse.class);
        assertThat(memberResponse.getId()).isNotNull();
        assertThat(memberResponse.getEmail()).isEqualTo(email);
        assertThat(memberResponse.getAge()).isEqualTo(age);
    }

    public static void 회원_정보_수정됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 회원_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 권한이_없음(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }
}
