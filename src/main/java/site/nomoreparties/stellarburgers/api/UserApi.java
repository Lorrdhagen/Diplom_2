package site.nomoreparties.stellarburgers.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.nomoreparties.stellarburgers.api.model.UserModel;
import site.nomoreparties.stellarburgers.api.model.UserCredentials;

import static io.restassured.RestAssured.given;

public class UserApi extends BaseApiSpec {

    private final String REGISTER_URL = "/api/auth/register";
    private final String LOGIN_URL = "/api/auth/login";
    private final String UPD_DEL_URL = "/api/auth/user";

    @Step("Создать пользователя")
    public Response createUser(UserModel userModel) {
        return given()
                .spec(getInitSpec())
                .body(userModel)
                .when()
                .post(REGISTER_URL);
    }

    @Step("Логин пользователя")
    public Response loginUser(UserCredentials userCredentials) {
        return given()
                .spec(getInitSpec())
                .body(userCredentials)
                .when()
                .post(LOGIN_URL);
    }

    @Step("Обновить  данные пользователя с авторизацией")
    public Response updateUserAuth(UserModel userModel, String accessToken) {
        return given()
                .spec(getInitSpec())
                .header("Authorization", accessToken)
                .body(userModel)
                .when()
                .patch(UPD_DEL_URL);
    }

    @Step("Обновить  данные пользователя без авторизации")
    public Response updateUserNotAuth(UserModel userModel) {
        return given()
                .spec(getInitSpec())
                .body(userModel)
                .when()
                .patch(UPD_DEL_URL);
    }

    @Step("Удалить пользователя")
    public boolean deleteUser(String accessToken) {
        return given()
                .spec(getInitSpecWithoutContentType())
                .header("Authorization", accessToken)
                .when()
                .delete(UPD_DEL_URL)
                .then()
                .assertThat().statusCode(202)
                .extract().path("success");
    }
}
