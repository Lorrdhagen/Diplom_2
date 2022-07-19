package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.UserApi;
import site.nomoreparties.stellarburgers.api.OrderApi;
import site.nomoreparties.stellarburgers.api.model.OrderIngredients;
import site.nomoreparties.stellarburgers.api.model.UserModel;
import site.nomoreparties.stellarburgers.api.model.OrderListUnauthorized;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class OrderListTest {

    private String accessToken;
    private static UserModel userModel;
    private static UserApi userApi;
    private static OrderApi orderApi;

    @Before
    public void init() {
        userModel = UserModel.getRandomUser();
        userApi = new UserApi();
        orderApi = new OrderApi();
    }

    @After
    public void clear() {
        if (!(accessToken == null)) {
            userApi.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Получение списка заказов пользователя с авторизацией")
    @Description("Получение списка заказов пользователя с авторизацией, проверка статус-кода и списка orders в теле ответа")
    public void getUserOrdersListWithAuth() {
        List<String> ingridientList = new ArrayList<>();
        ingridientList.add("61c0c5a71d1f82001bdaaa6c");
        ingridientList.add("61c0c5a71d1f82001bdaaa78");
        OrderIngredients orderIngredients = new OrderIngredients(ingridientList);
        Response respCreate = userApi.createUser(userModel);
        accessToken = respCreate.body().jsonPath().getString("accessToken");
        orderApi.createOrderAuth(orderIngredients, accessToken);
        ingridientList.add("61c0c5a71d1f82001bdaaa7a");
        orderIngredients.setIngredients(ingridientList);
        orderApi.createOrderAuth(orderIngredients, accessToken);
        Response respGetOrder = orderApi.getUserOrderListAuth(accessToken);
        assertEquals(SC_OK, respGetOrder.statusCode());
        assertNotNull(respGetOrder.body().jsonPath().getList("orders"));
    }

    @Test
    @DisplayName("Получение списка заказов пользователя без авторизаци")
    @Description("Получение списка заказов пользователя без авторизации, проверка статус-кода и поля message в теле ответа")
    public void getUserOrdersListNotAuth() {
        List<String> ingridientList = new ArrayList<>();
        ingridientList.add("61c0c5a71d1f82001bdaaa6c");
        ingridientList.add("61c0c5a71d1f82001bdaaa78");
        OrderIngredients orderIngredients = new OrderIngredients(ingridientList);
        Response respCreate = userApi.createUser(userModel);
        accessToken = respCreate.body().jsonPath().getString("accessToken");
        orderApi.createOrderAuth(orderIngredients, accessToken);
        Response respGetOrder = orderApi.getUserOrdersNotAuth();
        assertEquals(SC_UNAUTHORIZED, respGetOrder.statusCode());
        OrderListUnauthorized orderListUnauthorized = respGetOrder.as(OrderListUnauthorized.class);
        assertEquals(orderListUnauthorized.getUnauthMessage(), orderListUnauthorized.getMessage());
    }
}
