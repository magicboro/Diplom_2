package site.nomoreparties.stellarburgers.user;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@Epic("Проверки ошибок создания пользователей с пустыми полями")
@Feature("Проверки ошибок создания пользователей с пустыми полями")
@RunWith(Parameterized.class)
public class UserCreateFieldsTest {

    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    private final User user;

    public UserCreateFieldsTest(User user) {
        this.user = user;
    }


    @Parameterized.Parameters(name = "User registration credentials as {0}")
    public static Object[][] dataGenerator() {
        return new Object[][] {
                {User.emptyAllFields()},
                {User.emptyEmail()},
                {User.emptyPassword()},
                {User.emptyName()}
        };
    }

    @Test
    @DisplayName("Ошибка при создании пользователя, когда поля пустые")
    public void userEmptyFieldsCreationTest() {
        ValidatableResponse createResponse = client.createUser(user);
        check.requiredFieldsEmptyError(createResponse);
    }
}
