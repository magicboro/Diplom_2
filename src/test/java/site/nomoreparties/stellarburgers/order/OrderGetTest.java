package site.nomoreparties.stellarburgers.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import site.nomoreparties.stellarburgers.user.User;
import site.nomoreparties.stellarburgers.user.UserClient;

@Epic("Проверки получения заказов пользователя")
@Feature("Проверки получения заказов пользователя")
public class OrderGetTest {

    private final OrderClient orderClient = new OrderClient();
    private final OrderAssertions check = new OrderAssertions();
    private final UserClient userClient = new UserClient();
    private final User user = User.random();;
    private final String noAccessToken = "";
    private String accessToken;


    @Test
    @DisplayName("Успешное получение списка заказов для авторизованного пользователя с заказами")
    public void getUserOrdersWithOrdersSuccessTest() {
        ValidatableResponse createUserResponse = userClient.createUser(user);
        accessToken = userClient.getUserAccessToken(createUserResponse);
        var ingredients = OrderIngredients.correctIngredients();
        orderClient.createOrder(ingredients, accessToken);
        ValidatableResponse getResponse = orderClient.getUserOrders(accessToken);
        check.getUserOrdersWithOrdersSuccess(getResponse);

    }

    @Test
    @DisplayName("Успешное получение списка заказов для авторизованного пользователя без заказов")
    public void getUserOrdersWithoutOrdersSuccessTest() {
        ValidatableResponse createUserResponse = userClient.createUser(user);
        accessToken = userClient.getUserAccessToken(createUserResponse);
        ValidatableResponse getResponse = orderClient.getUserOrders(accessToken);
        check.getUserOrdersWithoutOrdersSuccess(getResponse);
    }

    @Test
    @DisplayName("401 ошибка при попытке получить список заказов без авторизации")
    public void getUserOrdersWithoutAuthErrorTest() {
        ValidatableResponse getResponse = orderClient.getUserOrders(noAccessToken);
        check.getOrdersNoAuthError(getResponse);
    }


    @After
    public void deleteUser() {
        if (accessToken != null && !accessToken.isEmpty()) {
            userClient.deleteUser(accessToken);
        }
    }

}
