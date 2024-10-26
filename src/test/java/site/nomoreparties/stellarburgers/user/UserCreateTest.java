package site.nomoreparties.stellarburgers.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


@Epic("Проверки создания пользователей")
@Feature("Проверки создания пользователей")
public class UserCreateTest {

    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    private User user;
    private String accessToken;

    @Before
    public void createUser() {
        user = User.random();
    }

    @Test
    @DisplayName("Успешное создание уникального пользователя")
    public void userSuccessCreationTest() {
        ValidatableResponse createResponse = client.createUser(user);
        check.createdSuccessfully(createResponse, user);
        accessToken = client.getUserAccessToken(createResponse);
    }

    @Test
    @DisplayName("Ошибка при создании пользователя, который уже существует")
    public void userAlreadyExistsCreationTest() {
        ValidatableResponse createFirstResponse = client.createUser(user);
        accessToken = client.getUserAccessToken(createFirstResponse);
        ValidatableResponse createSecondResponse = client.createUser(user);
        check.userAlreadyExistsError(createSecondResponse);
    }

    @After
    public void deleteUser() {
        if (accessToken != null && !accessToken.isEmpty()) {
            client.deleteUser(accessToken);
        }
    }


}
