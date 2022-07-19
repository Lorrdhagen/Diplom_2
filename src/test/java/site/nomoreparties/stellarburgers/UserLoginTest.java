package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.model.UserModel;
import site.nomoreparties.stellarburgers.api.UserApi;
import site.nomoreparties.stellarburgers.api.model.UserCredentials;
import site.nomoreparties.stellarburgers.api.model.LoginUserResponseRoot;
import site.nomoreparties.stellarburgers.api.model.LoginUserUnauthorized;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserLoginTest {

    private String accessToken;
    private static UserModel userModel;
    private static UserApi userApi;

    @Before
    public void init() {
        userModel = UserModel.getRandomUser();
        userApi = new UserApi();
    }

    @After
    public void clear() {
        if (!(accessToken == null)) {
            userApi.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Логин пользователя")
    @Description("Логин пользователя с корректными учетными данными, проверка статус-кода и поля success в теле ответа")
    public void userLoginWithCorrectCredentials() {
        userApi.createUser(userModel);
        UserCredentials userCredentials = new UserCredentials(userModel.getEmail(), userModel.getPassword());
        Response respLogin = userApi.loginUser(userCredentials);
        assertEquals(SC_OK, respLogin.statusCode());
        LoginUserResponseRoot loginUserResponseRoot = respLogin.as(LoginUserResponseRoot.class);
        accessToken = loginUserResponseRoot.getAccessToken();
        assertTrue(loginUserResponseRoot.isSuccess());
    }

    @Test
    @DisplayName("Логин пользователя без почты")
    @Description("Логин пользователя без почты, проверка статус-кода и поля message в теле ответа")
    public void userLoginWithoutEmail() {
        userModel.setEmail("");
        UserCredentials userCredentials = new UserCredentials(userModel.getEmail(), userModel.getPassword());
        Response respLogin = userApi.loginUser(userCredentials);
        assertEquals(SC_UNAUTHORIZED, respLogin.statusCode());
        LoginUserUnauthorized loginUserUnauthorized = respLogin.as(LoginUserUnauthorized.class);
        assertEquals(loginUserUnauthorized.getIncorrectCredentials(), loginUserUnauthorized.getMessage());
    }

    @Test
    @DisplayName("Логин пользователя без пароля")
    @Description("Логин пользователя без пароля, проверка статус-кода и поля message в теле ответа")
    public void userLoginWithoutPassword() {
        userModel.setPassword("");
        UserCredentials userCredentials = new UserCredentials(userModel.getEmail(), userModel.getPassword());
        Response respLogin = userApi.loginUser(userCredentials);
        assertEquals(SC_UNAUTHORIZED, respLogin.statusCode());
        LoginUserUnauthorized loginUserUnauthorized = respLogin.as(LoginUserUnauthorized.class);
        assertEquals(loginUserUnauthorized.getIncorrectCredentials(), loginUserUnauthorized.getMessage());
    }

    @Test
    @DisplayName("Логин пользователя с некорректными учетными данными")
    @Description("Логин пользователя с некорректными учетными данными, проверка статус-кода и поля message в теле ответа")
    public void userLoginWithWrongCredentials() {
        UserCredentials userCredentials = new UserCredentials(userModel.getEmail(), userModel.getPassword());
        Response respLogin = userApi.loginUser(userCredentials);
        assertEquals(SC_UNAUTHORIZED, respLogin.statusCode());
        LoginUserUnauthorized loginUserUnauthorized = respLogin.as(LoginUserUnauthorized.class);
        assertEquals(loginUserUnauthorized.getIncorrectCredentials(), loginUserUnauthorized.getMessage());
    }
}
