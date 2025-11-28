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
import static helpers.TestApiHelpers.*;
import static io.qameta.allure.Allure.step;

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
                executePost("/BookStore/v1/Books", token,
                        new AddListOfBooksModel(userId, BOOK_LIST), 201));

        step("Отправить запрос на удаление книги", () ->
                executeDelete("/BookStore/v1/Book", token,
                        new StringObjectModel(GIT_BOOK_ISBN, userId), 204));

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
                executePost("/BookStore/v1/Books", token,
                        new AddListOfBooksModel(userId, BOOK_LIST), 201));

        profilePage
                .openProfilePage()
                .checkingProfileBeforeDelete(AUTH_DATA.getUserName(), GIT_BOOK_TITLE)
                .deleteBooksInProfile()
                .checkingProfileAfterDelete(AUTH_DATA.getUserName());
    }
}
