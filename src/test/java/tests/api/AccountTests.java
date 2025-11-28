package tests.api;

import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import models.MessageModel;
import models.LoginViewModel;
import models.TokenViewModel;
import models.CreateUserResultModel;
import org.junit.jupiter.api.*;
import tests.TestBase;

import static data.TestData.*;
import static helpers.TestApiHelpers.executeDelete;
import static helpers.TestApiHelpers.executePost;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Tags({@Tag("all"), @Tag("api"), @Tag("account")})
@Owner("sergeyglukhov")
@Feature("Работа с данными пользователей через API")
@DisplayName("API тесты c данными пользователей на demoqa")
public class AccountTests extends TestBase {

    @Test
    @AllureId("41291")
    @Story("Авторизация")
    @DisplayName("Успешная авторизация через API")
    void successfulLoginWithTokenTest() {
        Response response = step("Отправить запрос на авторизацию", () ->
                executePost("/Account/v1/Authorized", AUTH_DATA, 200)
                        .extract().response());

        step("Проверить ответ", () -> {
            Boolean authorized = response.as(Boolean.class);
            assertThat(authorized).isNotNull();
            assertThat(authorized).isTrue();
        });
    }

    @Test
    @AllureId("41287")
    @Story("Авторизация")
    @DisplayName("Неуспешная авторизация с рандомными данными через API")
    void unsuccessfulLoginWithTokenTest() {
        Response response = step("Отправить запрос на авторизацию", () ->
                executePost("/Account/v1/Authorized", randomAuthData(), 404)
                        .extract().response());

        step("Проверить ответ", () -> {
            assertThat(response.path("code").toString()).isEqualTo("1207");
            assertThat(response.path("message").toString()).isEqualTo("User not found!");
        });
    }

    @Test
    @AllureId("41290")
    @Story("Получение токена")
    @DisplayName("Успешное получение токена через API")
    void successfulGenerateTokenTest() {
        Response response = step("Отправить запрос на получение токена", () ->
                executePost("/Account/v1/GenerateToken", AUTH_DATA, 200)
                        .extract().response());

        step("Проверить ответ", () -> {
            assertThat(response.path("token").toString()).isNotNull();
            assertThat(response.path("status").toString()).isEqualTo("Success");
            assertThat(response.path("result").toString()).isEqualTo("User authorized successfully.");
        });
    }

    @Test
    @AllureId("41288")
    @Story("Получение токена")
    @DisplayName("Неуспешное получение токена через API")
    void unsuccessfulGenerateTokenTest() {
        Response response = step("Отправить запрос на получение токена", () ->
                executePost("/Account/v1/GenerateToken", randomAuthData(), 200)
                        .extract().response());

        step("Проверить ответ", () -> {
            assertThat((Object) response.path("token")).isNull();
            assertThat(response.path("status").toString()).isEqualTo("Failed");
            assertThat(response.path("result").toString()).isEqualTo("User authorization failed.");
        });
    }

    @Test
    @AllureId("41292")
    @Story("Повторная регистрация")
    @DisplayName("Неуспешная повторная регистрация уже зарегистрированного пользователя")
    void userReRegistrationTest() {
        Response response = step("Отправить запрос на регистрацию", () ->
                executePost("/Account/v1/User", AUTH_DATA, 406)
                        .extract().response());

        step("Проверить ответ", () -> {
            assertThat(response.path("code").toString()).isEqualTo("1204");
            assertThat(response.path("message").toString()).isEqualTo("User exists!");
        });
    }

    @Test
    @AllureId("41289")
    @Story("Добавление и удаление пользователя")
    @DisplayName("Успешное добавление и удаление нового пользователя")
    void addAndDeleteUserTest() {
        LoginViewModel randomAuthDataForThisTest = randomAuthData();

        CreateUserResultModel regResponse = step("Отправить запрос на регистрацию нового пользователя", () ->
                executePost("/Account/v1/User", randomAuthDataForThisTest, 201)
                        .extract().as(CreateUserResultModel.class));

        step("Проверить регистрацию нового пользователя", () ->
                assertThat(regResponse.getUsername()).isEqualTo(randomAuthDataForThisTest.getUserName()));

        TokenViewModel genTokenResponse = step("Отправить запрос на авторизацию", () ->
                executePost("/Account/v1/GenerateToken", randomAuthDataForThisTest, 200)
                        .extract().as(TokenViewModel.class));

        step("Проверить ответ на авторизацию", () -> {
            assertThat(genTokenResponse.getStatus()).isEqualTo("Success");
            assertThat(genTokenResponse.getResult()).isEqualTo("User authorized successfully.");
        });

        step("Отправить запрос на удаление", () ->
                executeDelete(
                        "/Account/v1/User/{id}", regResponse.getUserID(),
                        genTokenResponse.getToken(), 204));

        MessageModel finalResponse = step("Отправить запрос на авторизацию удаленного пользователя", () ->
                executePost("/Account/v1/Authorized", randomAuthDataForThisTest, 404)
                        .extract().as(MessageModel.class));

        step("Проверить ответ, что пользователь не найден", () -> {
            assertThat(finalResponse.getCode()).isEqualTo("1207");
            assertThat(finalResponse.getMessage()).isEqualTo("User not found!");
        });
    }
}