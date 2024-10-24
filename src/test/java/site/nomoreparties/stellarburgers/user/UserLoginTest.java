package site.nomoreparties.stellarburgers.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;


@Epic("Проверки авторизации пользователя")
@Feature("Проверки авторизации пользователя")
public class UserLoginTest {

    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();

    String accessToken;

    @Test
    @DisplayName("Успешная авторизация пользователя")
    public void userSuccessAuthTest() {
        var user = User.random();
        var createResponse = client.createUser(user);
        accessToken = client.getUserAccessToken(createResponse);

        var userCredentials = UserCredentials.fromUser(user);
        ValidatableResponse loginResponse = client.loginUser(userCredentials);
        check.loginSuccessfully(loginResponse, user);

    }

    @Test
    @DisplayName("Логин с некорректным логином")
    public void userWrongLoginAuthTest() {
        var user = User.random();
        var createResponse = client.createUser(user);
        accessToken = client.getUserAccessToken(createResponse);

        var userCredentials = new UserCredentials("wrongEmail@email.com", user.getPassword());
        ValidatableResponse loginResponse = client.loginUser(userCredentials);
        check.loginWrongEmailPasswordError(loginResponse);
    }

    @Test
    @DisplayName("Логин с некорректным паролем")
    public void userWrongPassAuthTest() {
        var user = User.random();
        var createResponse = client.createUser(user);
        accessToken = client.getUserAccessToken(createResponse);

        var userCredentials = new UserCredentials(user.getEmail(), "wrongPassword");
        ValidatableResponse loginResponse = client.loginUser(userCredentials);
        check.loginWrongEmailPasswordError(loginResponse);
    }

    @Test
    @DisplayName("Логин с некорректным логином/паролем")
    public void userWrongCredentialsAuthTest() {
        var user = User.random();
        var createResponse = client.createUser(user);
        accessToken = client.getUserAccessToken(createResponse);

        var userCredentials = new UserCredentials("wrongEmail", "wrongPassword");
        ValidatableResponse loginResponse = client.loginUser(userCredentials);
        check.loginWrongEmailPasswordError(loginResponse);
    }

    @After
    public void deleteUser() {
        if (!accessToken.isEmpty())
            client.deleteUser(accessToken);
    }

}
