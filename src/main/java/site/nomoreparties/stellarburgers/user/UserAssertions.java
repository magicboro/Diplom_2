package site.nomoreparties.stellarburgers.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import java.util.Map;
import java.util.Set;
import static java.net.HttpURLConnection.*;

public class UserAssertions {

    @Step("Assert that success user creation response have correct status code and body")
    public void createdSuccessfully(ValidatableResponse createResponse, User user) {
        System.out.println("ВЫЗОВ ПРОВЕРКИ СОЗДАНИЯ ПОЛЬЗОВАТЕЛЯ!!!!!!!!!!!!!");
        var responseBody = createResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .body().as(Map.class);

        Assert.assertEquals(true, responseBody.get("success"));
        Assert.assertEquals(Set.of("success", "user", "accessToken", "refreshToken"), responseBody.keySet());
        Map<String, Object> userResponse = (Map<String, Object>) responseBody.get("user");
        Assert.assertNotNull(userResponse);
        Assert.assertEquals(user.getEmail().toLowerCase(), userResponse.get("email"));
        Assert.assertEquals(user.getName(), userResponse.get("name"));
        Assert.assertNotNull(responseBody.get("accessToken"));
        Assert.assertNotNull(responseBody.get("refreshToken"));
    }

    @Step("Assert that same user registration error response have correct status code and body")
    public void userAlreadyExistsError(ValidatableResponse createResponse) {
        var responseBody = createResponse
                .assertThat()
                .statusCode(403)
                .extract()
                .body().as(Map.class);
        Assert.assertEquals(false, responseBody.get("success"));
        Assert.assertEquals("User already exists", responseBody.get("message"));
    }

    @Step("Assert that required empty fields registration error response have correct status code and body")
    public void requiredFieldsEmptyError(ValidatableResponse createResponse) {
        var responseBody = createResponse
                .assertThat()
                .statusCode(403)
                .extract()
                .body().as(Map.class);

        Assert.assertEquals(false, responseBody.get("success"));
        Assert.assertEquals(Set.of("success", "message"), responseBody.keySet());
        Assert.assertEquals("Email, password and name are required fields", responseBody.get("message"));
    }


}
