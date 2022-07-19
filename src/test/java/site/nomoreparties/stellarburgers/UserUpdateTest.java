package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.UserApi;
import site.nomoreparties.stellarburgers.api.model.UserModel;
import site.nomoreparties.stellarburgers.api.model.UpdateUserRersponseRoot;
import site.nomoreparties.stellarburgers.api.model.UpdateUserUnauthForbidden;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class UserUpdateTest {

    private String accessToken;
    private String getAccessToken2;
    private static UserModel userModel;
    private static UserApi userApi;

    @Before
    public void init() {
        userModel = UserModel.getRandomUser();
        userApi = new UserApi();
    }

    @After
    public void clear() {
        if (!(accessToken == null) && !(getAccessToken2 == null)) {
            userApi.deleteUser(accessToken);
            userApi.deleteUser(getAccessToken2);
        } else {
            userApi.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Обновление данных пользователя с авторизацией")
    @Description("Обновление почты, пароля и имени пользователя с авторизацией, проверка статус-кода и поля success в теле ответа")
    public void userUpdateWithAuthAndCorrectData() {
        Response respCreate = userApi.createUser(userModel);
        accessToken = respCreate.body().jsonPath().getString("accessToken");
        userModel.setEmail("mail777@test.ru");
        userModel.setPassword("pss12345");
        userModel.setName("Newname");
        Response respUpdate = userApi.updateUserAuth(userModel, accessToken);
        assertEquals(SC_OK, respUpdate.statusCode());
        UpdateUserRersponseRoot updateUserRersponseRoot = respUpdate.as(UpdateUserRersponseRoot.class);
        assertTrue(updateUserRersponseRoot.isSuccess());
    }

    @Test
    @DisplayName("Обновление данных пользователя без авторизации")
    @Description("Обновление данных пользователя без авторизации, проверка статус-кода и поля message в теле ответа")
    public void userUpdateNotAuth() {
        Response respCreate = userApi.createUser(userModel);
        accessToken = respCreate.body().jsonPath().getString("accessToken");
        userModel.setName("Newname");
        Response respUpdate = userApi.updateUserNotAuth(userModel);
        assertEquals(SC_UNAUTHORIZED, respUpdate.statusCode());
        UpdateUserUnauthForbidden updateUserUnauthForbidden = respUpdate.as(UpdateUserUnauthForbidden.class);
        assertEquals(updateUserUnauthForbidden.getUnauthMessage(), updateUserUnauthForbidden.getMessage());
    }

    @Test
    @DisplayName("Обновление почты пользователя на уже используемую")
    @Description("Обновление почты пользователя на уже используемую другим пользователем, проверка статус-кода и поля message в теле ответа")
    public void userUpdateWithMailAlreadyInUse() {
        userModel.setEmail("newmail@test.ru");
        Response respCreate = userApi.createUser(userModel);
        accessToken = respCreate.body().jsonPath().getString("accessToken");
        userModel = UserModel.getRandomUser();
        Response respCreate2 = userApi.createUser(userModel);
        getAccessToken2 = respCreate2.body().jsonPath().getString("accessToken");
        userModel.setEmail("newmail@test.ru");
        Response respUpdate = userApi.updateUserAuth(userModel, getAccessToken2);
        assertEquals(SC_FORBIDDEN, respUpdate.statusCode());
        UpdateUserUnauthForbidden updateUserUnauthForbidden = respUpdate.as(UpdateUserUnauthForbidden.class);
        assertEquals(updateUserUnauthForbidden.getForbiddenMessage(), updateUserUnauthForbidden.getMessage());
    }
}
