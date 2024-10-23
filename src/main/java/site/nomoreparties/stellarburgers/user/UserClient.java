package site.nomoreparties.stellarburgers.user;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import site.nomoreparties.stellarburgers.EnvPaths;
import static io.restassured.RestAssured.given;

public class UserClient {

    @Step("Create user by method : " + EnvPaths.USER_REG_PATH)
    public ValidatableResponse createUser(User user) {
        System.out.println("ВЫЗОВ МЕТОДА СОЗДАНИЯ ПОЛЬЗОВАТЕЛЯ!!!!!!!!!!!!!");
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(EnvPaths.BASE_URI)
                .body(user)
                .when()
                .post(EnvPaths.USER_REG_PATH)
                .then().log().all();

    }

    @Step("Delete user by method : " + EnvPaths.USER_REG_PATH)
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(EnvPaths.BASE_URI)
                .auth().oauth2(accessToken)
                .delete(EnvPaths.USER_PATH)
                .then().log().all();
    }
}
