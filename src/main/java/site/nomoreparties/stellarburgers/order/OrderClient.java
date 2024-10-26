package site.nomoreparties.stellarburgers.order;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import site.nomoreparties.stellarburgers.EnvPaths;
import static io.restassured.RestAssured.given;

public class OrderClient {

    @Step("Create order by method: " + EnvPaths.ORDERS_PATH)
    public ValidatableResponse createOrder(OrderIngredients ingredients, String accessToken) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(EnvPaths.BASE_URI)
                .auth().oauth2(accessToken)
                .body(ingredients)
                .when()
                .post(EnvPaths.ORDERS_PATH)
                .then().log().all();
    }

    @Step("Get user orders: " + EnvPaths.ORDERS_PATH)
    public ValidatableResponse getUserOrders(String accessToken) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(EnvPaths.BASE_URI)
                .auth().oauth2(accessToken)
                .when()
                .get(EnvPaths.ORDERS_PATH)
                .then().log().all();
    }

}
