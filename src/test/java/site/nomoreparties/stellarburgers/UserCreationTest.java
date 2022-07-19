package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.api.model.UserModel;
import site.nomoreparties.stellarburgers.api.UserApi;
import site.nomoreparties.stellarburgers.api.model.CreateUserResponseRoot;
import site.nomoreparties.stellarburgers.api.model.CreateUserForbidden;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class UserCreationTest {

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
    @DisplayName("Создание пользователя")
    @Description("Создание пользователя с корректными учетными данными, проверка статус-кода и поля success в теле ответа")
    public void userCreationWithValidData() {
        Response respCreate = userApi.createUser(userModel);
        assertEquals(SC_OK, respCreate.statusCode());
        CreateUserResponseRoot createUserResponseRoot = respCreate.as(CreateUserResponseRoot.class);
        accessToken = createUserResponseRoot.getAccessToken();
        assertTrue(createUserResponseRoot.isSuccess());
    }

    @Test
    @DisplayName("Создание уже зарегистрированного пользователя")
    @Description("Создание уже зарегистрированного пользователя, проверка статус-кода и поля message в теле ответа")
    public void creatingAnExistingUser() {
        Response respFirst = userApi.createUser(userModel);
        CreateUserResponseRoot createUserResponseRoot = respFirst.as(CreateUserResponseRoot.class);
        accessToken = createUserResponseRoot.getAccessToken();
        Response respCreate = userApi.createUser(userModel);
        assertEquals(SC_FORBIDDEN, respCreate.statusCode());
        CreateUserForbidden createUserForbidden = respCreate.as(CreateUserForbidden.class);
        assertEquals(createUserForbidden.getUserExists(), createUserForbidden.getMessage());
    }

    @Test
    @DisplayName("Создание пользователя без почты")
    @Description("Создание пользователя без почты, проверка статус-кода и поля message в теле ответа")
    public void userCreationWithoutEmail() {
        userModel.setEmail("");
        Response respCreate = userApi.createUser(userModel);
        assertEquals(SC_FORBIDDEN, respCreate.statusCode());
        CreateUserForbidden createUserForbidden = respCreate.as(CreateUserForbidden.class);
        assertEquals(createUserForbidden.getRequiredFields(), createUserForbidden.getMessage());
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    @Description("Создание пользователя без пароля, проверка статус-кода и поля message в теле ответа")
    public void userCreationWithoutPassword() {
        userModel.setPassword("");
        Response respCreate = userApi.createUser(userModel);
        assertEquals(SC_FORBIDDEN, respCreate.statusCode());
        CreateUserForbidden createUserForbidden = respCreate.as(CreateUserForbidden.class);
        assertEquals(createUserForbidden.getRequiredFields(), createUserForbidden.getMessage());
    }

    @Test
    @DisplayName("Создание пользователя без имени")
    @Description("Создание пользователя без имени, проверка статус-кода и поля message в теле ответа")
    public void userCreationWithoutName() {
        userModel.setName("");
        Response respCreate = userApi.createUser(userModel);
        assertEquals(SC_FORBIDDEN, respCreate.statusCode());
        CreateUserForbidden createUserForbidden = respCreate.as(CreateUserForbidden.class);
        assertEquals(createUserForbidden.getRequiredFields(), createUserForbidden.getMessage());
    }
}
