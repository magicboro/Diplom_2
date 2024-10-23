package site.nomoreparties.stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class UserCreateTest {

    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();

    String accessToken;

    private void getUserAccessToken(ValidatableResponse createResponse) {
        String accessTokenWithBearer = createResponse.extract().path("accessToken");
        accessToken = accessTokenWithBearer.replace("Bearer ", "");
    }

    @Test
    @DisplayName("Успешное создание уникального пользователя")
    public void userSuccessCreationTest() {
        var user = User.random();
        ValidatableResponse createResponse = client.createUser(user);
        check.createdSuccessfully(createResponse, user);
        getUserAccessToken(createResponse);
    }

    @Test
    @DisplayName("Ошибка при создании пользователя, который уже существует")
    public void userAlreadyExistsCreationTest() {
        var user = User.random();
        ValidatableResponse createFirstResponse = client.createUser(user);
        getUserAccessToken(createFirstResponse);
        ValidatableResponse createSecondResponse = client.createUser(user);
        check.userAlreadyExistsError(createSecondResponse);
    }

    @After
    public void deleteUser() {
        if (!accessToken.isEmpty())
            client.deleteUser(accessToken);
    }


}
