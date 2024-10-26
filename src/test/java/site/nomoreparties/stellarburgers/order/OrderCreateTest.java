package site.nomoreparties.stellarburgers.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import site.nomoreparties.stellarburgers.user.User;
import site.nomoreparties.stellarburgers.user.UserClient;


@Epic("Проверки создания заказов")
@Feature("Проверки создания заказов")
public class OrderCreateTest {

    private final OrderClient orderClient = new OrderClient();
    private final OrderAssertions check = new OrderAssertions();
    private final UserClient userClient = new UserClient();
    private final User user = User.random();;
    private final String noAccessToken = "";
    private String accessToken;


    @Test
    @DisplayName("Успешное создание заказа без авторизации")
    public void orderSuccessCreationWithoutAuthTest() {
        var ingredients = OrderIngredients.correctIngredients();
        ValidatableResponse createResponse = orderClient.createOrder(ingredients, noAccessToken);
        check.createdSuccessfully(createResponse);
    }

    @Test
    @DisplayName("Успешное создание заказа c авторизацией")
    public void orderSuccessCreationWithAuthTest() {
        ValidatableResponse createUserResponse = userClient.createUser(user);
        accessToken = userClient.getUserAccessToken(createUserResponse);

        var ingredients = OrderIngredients.correctIngredients();
        ValidatableResponse createOrderResponse = orderClient.createOrder(ingredients, accessToken);
        check.createdSuccessfullyAuth(createOrderResponse);

        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("400 ошибка при попытке создания заказа без ингридиентов")
    public void orderEmptyIngredientsErrorTest() {
        var ingredients = OrderIngredients.emptyIngredients();
        ValidatableResponse createResponse = orderClient.createOrder(ingredients, noAccessToken);
        check.noIngredientsError(createResponse);
    }

    @Test
    @DisplayName("500 ошибка при попытке создания заказа c некорректным хешем")
    public void orderWrongIngredientHashErrorTest() {
        var ingredients = OrderIngredients.wrongHashIngredients();
        ValidatableResponse createResponse = orderClient.createOrder(ingredients,noAccessToken);
        check.wrongIngredientHashError(createResponse);
    }


}
