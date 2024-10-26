package site.nomoreparties.stellarburgers.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static java.net.HttpURLConnection.*;

public class OrderAssertions {


    @Step("Assert that success order creation without authentication response have correct status code and body")
    public void createdSuccessfully(ValidatableResponse createResponse)
    {
        var responseBody = createResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .body().as(Map.class);

        Assert.assertEquals(Set.of("success", "name", "order"), responseBody.keySet());
        Assert.assertEquals(true, responseBody.get("success"));
        Assert.assertNotNull(responseBody.get("name"));
        Map<String, Object> orderResponse = (Map<String, Object>) responseBody.get("order");
        Assert.assertNotNull(orderResponse);
        Assert.assertEquals(Set.of("number"), orderResponse.keySet());
    }

    @Step("Assert that success order creation with authentication response have correct status code and body")
    public void createdSuccessfullyAuth(ValidatableResponse createResponse)
    {
        var responseBody = createResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .body().as(Map.class);

        Assert.assertEquals(Set.of("success", "name", "order"), responseBody.keySet());
        Assert.assertEquals(true, responseBody.get("success"));
        Assert.assertNotNull(responseBody.get("name"));
        Map<String, Object> orderResponse = (Map<String, Object>) responseBody.get("order");
        Assert.assertNotNull(orderResponse);
        Assert.assertEquals(Set.of("ingredients", "_id", "owner", "status", "name", "createdAt", "updatedAt", "number", "price"), orderResponse.keySet());
    }

    @Step("Assert that order creation without ingredients error response have correct status code and body")
    public void noIngredientsError(ValidatableResponse createResponse)
    {
        var responseBody = createResponse
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .extract()
                .body().as(Map.class);

        Assert.assertEquals(Set.of("success", "message"), responseBody.keySet());
        Assert.assertEquals(false, responseBody.get("success"));
        Assert.assertEquals("Ingredient ids must be provided", responseBody.get("message"));
    }

    @Step("Assert that order creation with wrong ingredients hash error response have correct status code")
    public void wrongIngredientHashError(ValidatableResponse createResponse)
    {
        createResponse
                .assertThat()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

    @Step("Assert that success get user orders (=0) response have correct status code and body")
    public void getUserOrdersWithoutOrdersSuccess(ValidatableResponse createResponse)
    {
        var responseBody = createResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .body().as(Map.class);

        Assert.assertEquals(Set.of("success", "orders", "total", "totalToday"), responseBody.keySet());
        Assert.assertEquals(true, responseBody.get("success"));;
        Assert.assertNotNull(responseBody.get("total"));
        Assert.assertNotNull(responseBody.get("totalToday"));
        List<Map<String, Object>> orders = (List<Map<String, Object>>) responseBody.get("orders");
        Assert.assertTrue("Orders list is not empty", orders.isEmpty());

    }

    @Step("Assert that success get user orders (>0) response have correct status code and body")
    public void getUserOrdersWithOrdersSuccess(ValidatableResponse createResponse)
    {
        var responseBody = createResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .body().as(Map.class);

        Assert.assertEquals(Set.of("success", "orders", "total", "totalToday"), responseBody.keySet());
        Assert.assertEquals(true, responseBody.get("success"));
        Assert.assertNotNull(responseBody.get("total"));
        Assert.assertNotNull(responseBody.get("totalToday"));
        List<Map<String, Object>> orders = (List<Map<String, Object>>) responseBody.get("orders");
        Assert.assertFalse("Orders list is empty", orders.isEmpty());

        for (Map<String, Object> order : orders) {
            Assert.assertNotNull(order.get("_id"));
            Assert.assertNotNull(order.get("ingredients"));
            Assert.assertNotNull(order.get("status"));
            Assert.assertNotNull(order.get("name"));
            Assert.assertNotNull(order.get("createdAt"));
            Assert.assertNotNull(order.get("updatedAt"));
            Assert.assertNotNull(order.get("number"));
        }
    }

    @Step("Assert that get user orders without authentication error response have correct status code and body")
    public void getOrdersNoAuthError(ValidatableResponse createResponse)
    {
        var responseBody = createResponse
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .extract()
                .body().as(Map.class);

        Assert.assertEquals(Set.of("success", "message"), responseBody.keySet());
        Assert.assertEquals(false, responseBody.get("success"));
        Assert.assertEquals("You should be authorised", responseBody.get("message"));
    }


}
