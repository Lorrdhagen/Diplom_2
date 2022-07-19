package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.OrderApi;
import site.nomoreparties.stellarburgers.api.model.OrderIngredients;
import site.nomoreparties.stellarburgers.api.model.UserModel;
import site.nomoreparties.stellarburgers.api.UserApi;
import site.nomoreparties.stellarburgers.api.model.OrderErrorsResponse;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

public class OrderCreationTest {

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
    @DisplayName("Создание заказа с авторизацией, ингридиенты валидные")
    @Description("Создание заказа с авторизацией и валидными ингридиентами, проверка статус-кода и поля success в теле ответа")
    public void orderCteateWithAuthAndValidIngredients() {
        List<String> ingredientsList = new ArrayList<>();
        ingredientsList.add("61c0c5a71d1f82001bdaaa6d");
        ingredientsList.add("61c0c5a71d1f82001bdaaa6f");
        OrderIngredients orderIngredients = new OrderIngredients(ingredientsList);
        Response respCreate = userApi.createUser(userModel);
        accessToken = respCreate.body().jsonPath().getString("accessToken");
        Response respOrder = orderApi.createOrderAuth(orderIngredients, accessToken);
        assertEquals(SC_OK, respOrder.statusCode());
        assertTrue(respOrder.body().jsonPath().getBoolean("success"));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией, ингридиенты невалидные")
    @Description("Создание заказа с авторизацией и невалидными ингридиентами, проверка статус-кода")
    public void orderCreateWithAuthAndInvalidIngrients() {
        List<String> ingredientList = new ArrayList<>();
        ingredientList.add("61c0c5a71d1f82001bdhhh6c");
        ingredientList.add("61c0c5a71d1f82001bdhhh76");
        OrderIngredients orderIngredients = new OrderIngredients(ingredientList);
        Response respCreate = userApi.createUser(userModel);
        accessToken = respCreate.body().jsonPath().getString("accessToken");
        Response respOrder = orderApi.createOrderAuth(orderIngredients, accessToken);
        assertEquals(SC_INTERNAL_SERVER_ERROR, respOrder.statusCode());
    }

    @Test
    @DisplayName("Создание заказа с авторизацией, без ингридиентов")
    @Description("Создание заказа с авторизацией и пустым списком ингридиентов, проверка статус-кода и поля message в теле ответа")
    public void orderCreateWithAuthAndEmptyIngredientList() {
        List<String> ingredientList = new ArrayList<>();
        OrderIngredients orderIngredients = new OrderIngredients(ingredientList);
        Response respCreate = userApi.createUser(userModel);
        accessToken = respCreate.body().jsonPath().getString("accessToken");
        Response respOrder = orderApi.createOrderAuth(orderIngredients, accessToken);
        assertEquals(SC_BAD_REQUEST, respOrder.statusCode());
        OrderErrorsResponse orderErrorsResponse = respOrder.as(OrderErrorsResponse.class);
        assertEquals(orderErrorsResponse.getBadRequestMessage(), orderErrorsResponse.getMessage());
    }

    @Test
    @DisplayName("Создание заказа без авторизации, ингридиенты валидные")
    @Description("Создание заказа без авторизации и с валидными ингридиентами, проверка статус-кода и поля success в теле ответа")
    public void orderCreateNotAuthWithValidIngredients() {
        List<String> ingredientList = new ArrayList<>();
        ingredientList.add("61c0c5a71d1f82001bdaaa6d");
        ingredientList.add("61c0c5a71d1f82001bdaaa77");
        OrderIngredients orderIngredients = new OrderIngredients(ingredientList);
        Response respOrder = orderApi.createOrderNotAuth(orderIngredients);
        assertEquals(SC_UNAUTHORIZED, respOrder.statusCode());
        assertFalse(respOrder.body().jsonPath().getBoolean("success"));
    }
}
