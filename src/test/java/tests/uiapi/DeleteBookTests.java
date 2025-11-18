package tests.uiapi;

import helpers.auth.AuthContext;
import helpers.auth.WithLogin;
import io.qameta.allure.*;
import models.AddListOfBooksModel;
import models.StringObjectModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static data.TestData.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.BookStoreSpecs.baseReqSpec;
import static specs.BookStoreSpecs.baseRespSpec;

@Tags({@Tag("all"), @Tag("uiapi"), @Tag("account"), @Tag("bookstore")})
@Owner("sergeyglukhov")
@Link(value = "Profile", url = "https://demoqa.com/profile")
@Feature("Удаление книг из профиля")
@DisplayName("UI+API тесты на удаление книг из профиля в Book Store на demoqa")
public class DeleteBookTests extends TestBase {

    @Test
    @AllureId("41301")
    @WithLogin
    @Story("Удаление через API запрос")
    @DisplayName("Успешное удаление товара из списка через API запрос")
    void successfulDeleteBookFromListWithApiRequestTest() {
        String
                token = AuthContext.getToken(),
                userId = AuthContext.getUserId();

        step("Отправить запрос на добавление книги", () ->
                given(baseReqSpec)
                        .header("Authorization", "Bearer " + token)
                        .body(new AddListOfBooksModel(userId, BOOK_LIST))
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .spec(baseRespSpec(201)));

        step("Отправить запрос на удаление книги", () ->
                given(baseReqSpec)
                        .header("Authorization", "Bearer " + token)
                        .body(new StringObjectModel(GIT_BOOK_ISBN, userId))
                        .when()
                        .delete("/BookStore/v1/Book")
                        .then()
                        .spec(baseRespSpec(204)));

        profilePage
                .openProfilePage()
                .checkingProfileAfterDelete(AUTH_DATA.getUserName());
    }

    @Test
    @AllureId("41302")
    @WithLogin
    @Story("Удаление через UI интерфейс")
    @DisplayName("Успешное удаление товара из списка через UI взаимодействие")
    void successfulDeleteBookFromListWithUiTest() {
        String
                token = AuthContext.getToken(),
                userId = AuthContext.getUserId();

        step("Отправить запрос на добавление книги через API", () ->
                given(baseReqSpec)
                        .header("Authorization", "Bearer " + token)
                        .body(new AddListOfBooksModel(userId, BOOK_LIST))
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .spec(baseRespSpec(201)));

        profilePage
                .openProfilePage()
                .checkingProfileBeforeDelete(AUTH_DATA.getUserName(), GIT_BOOK_TITLE)
                .deleteBooksInProfile()
                .checkingProfileAfterDelete(AUTH_DATA.getUserName());
    }
}
