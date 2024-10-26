package site.nomoreparties.stellarburgers.user;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserUpdateTest {

    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    private User user;
    private String accessToken;

    @Before
    public void createUser() {
        user = User.random();
        var createResponse = client.createUser(user);
        accessToken = client.getUserAccessToken(createResponse);
    }

    @Test
    @DisplayName("Успешное изменение данных авторизованного пользователя")
    public void userChangeInfoSuccessTest() {
        User newUser = User.random();
        var changeResponse = client.changeUserCredentialsWithAuth(newUser, accessToken);
        check.changeUserDataSuccessfully(changeResponse, newUser);
    }

    @Test
    @DisplayName("Ошибка при попытке изменения почты пользователя, которая уже существует")
    public void userChangeEmailAlreadyExistsErrorTest() {
        User newUser = User.random();
        client.createUser(newUser);
        var changeResponse = client.changeUserCredentialsWithAuth(newUser, accessToken);
        check.changeUserDataEmailAlreadyExistsError(changeResponse);
    }

    @Test
    @DisplayName("Ошибка при попытке изменения данных без авторизации")
    public void userChangeInfoAuthErrorTest() {
        User newUser = User.random();
        var changeResponse = client.changeUserCredentialsWithoutAuth(newUser);
        check.changeUserDataWithoutAuthError(changeResponse);
    }

    @After
    public void deleteUser() {
        if (accessToken != null && !accessToken.isEmpty()) {
            client.deleteUser(accessToken);
        }
    }

}
