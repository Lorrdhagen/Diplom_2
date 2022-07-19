package site.nomoreparties.stellarburgers.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.api.model.UserModel;
import site.nomoreparties.stellarburgers.api.model.UserCredentials;

import static io.restassured.RestAssured.given;

public class UserApi extends BaseApiSpec {

    @Step("Создать пользователя")
    public Response createUser(UserModel userModel) {
        return given()
                .spec(getInitSpec())
                .body(userModel)
                .when()
                .post("/api/auth/register");

    }

    @Step("Логин пользователя")
    public Response loginUser(UserCredentials userCredentials) {
        return given()
                .spec(getInitSpec())
                .body(userCredentials)
                .when()
                .post("/api/auth/login");
    }

    @Step("Обновить  данные пользователя с авторизацией")
    public Response updateUserAuth(UserModel userModel, String accessToken) {
        return given()
                .spec(getInitSpec())
                .header("Authorization", accessToken)
                .body(userModel)
                .when()
                .patch("/api/auth/user");
    }

    @Step("Обновить  данные пользователя без авторизации")
    public Response updateUserNotAuth(UserModel userModel) {
        return given()
                .spec(getInitSpec())
                .body(userModel)
                .when()
                .patch("/api/auth/user");
    }

    @Step("Удалить пользователя")
    public boolean deleteUser(String accessToken) {
        return given()
                .spec(getInitSpecWithoutContentType())
                .header("Authorization", accessToken)
                .when()
                .delete("/api/auth/user")
                .then()
                .assertThat().statusCode(202)
                .extract().path("success");
    }
}
