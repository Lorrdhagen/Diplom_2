package site.nomoreparties.stellarburgers.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.api.model.OrderIngredients;

import static io.restassured.RestAssured.given;

public class OrderApi extends BaseApiSpec {

    private final String ORDERS_URL = "/api/orders";

    @Step("Создать заказ с авторизацией")
    public Response createOrderAuth(OrderIngredients orderIngredients, String accessToken) {
        return given()
                .spec(getInitSpec())
                .header("Authorization", accessToken)
                .body(orderIngredients)
                .when()
                .post(ORDERS_URL);
    }

    @Step("Создать заказ без авторизации")
    public Response createOrderNotAuth(OrderIngredients orderIngredients) {
        return given()
                .spec(getInitSpec())
                .body(orderIngredients)
                .when()
                .post(ORDERS_URL);
    }

    @Step("Получить список заказов пользователя с авторизацией")
    public Response getUserOrderListAuth(String accessToken) {
        return given()
                .spec(getInitSpec())
                .header("Authorization", accessToken)
                .when()
                .get(ORDERS_URL);
    }

    @Step("Получить список заказов пользователя без авторизации")
    public Response getUserOrdersNotAuth() {
        return given()
                .spec(getInitSpec())
                .when()
                .get(ORDERS_URL);
    }
}
