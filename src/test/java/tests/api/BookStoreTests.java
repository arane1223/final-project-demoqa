package tests.api;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import java.util.List;

import static data.TestData.*;
import static data.TestData.GIT_BOOK_AUTHOR;
import static data.TestData.GIT_BOOK_PAGES;
import static data.TestData.GIT_BOOK_PUBLISHER;
import static data.TestData.GIT_BOOK_PUBLISH_DATE;
import static data.TestData.GIT_BOOK_SUB_TITLE;
import static data.TestData.incorrectRandomIsbn;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.BookStoreSpecs.baseReqSpec;
import static specs.BookStoreSpecs.baseRespSpec;

@Tags({@Tag("all"), @Tag("api"), @Tag("bookstore")})
@Owner("sergeyglukhov")
@Feature("Работа с Book Store через API")
@DisplayName("API тесты c данными в Book Store на demoqa")
public class BookStoreTests extends TestBase {

    @Test
    @Story("Проверка по названию")
    @DisplayName("Проверка библиотеки книг по названиям")
    void checkingBookListWithGroovyTest() {
        Response response = step("Отправить запрос на получение списка книг", () ->
                given(baseReqSpec)
                        .get("/BookStore/v1/Books")
                        .then()
                        .spec(baseRespSpec(200))
                        .extract().response());

        step("Проверить ответ, сравнить полученный список книг с заданным списком", () -> {
            List<String> actualTitles = response.path("books.title");
            assertThat(actualTitles).isEqualTo(BOOKS);
        });
    }

    @Test
    @Story("Проверка данных книги")
    @DisplayName("Проверка характеристик книги по ISBN")
    void checkingBookCharacteristicsByIsbnWithGroovyTest() {
        Response response = step("Отправить запрос на получение книги «Git Pocket Guide»", () ->
                given(baseReqSpec)
                        .queryParam("ISBN", GIT_BOOK_ISBN)
                        .get("/BookStore/v1/Book")
                        .then()
                        .spec(baseRespSpec(200))
                        .extract().response());

        step("Проверить характеристики книги", () -> {
            assertThat(response.path("title").toString()).isEqualTo(GIT_BOOK_TITLE);
            assertThat(response.path("subTitle").toString()).isEqualTo(GIT_BOOK_SUB_TITLE);
            assertThat(response.path("author").toString()).isEqualTo(GIT_BOOK_AUTHOR);
            assertThat(response.path("publish_date").toString()).isEqualTo(GIT_BOOK_PUBLISH_DATE);
            assertThat(response.path("publisher").toString()).isEqualTo(GIT_BOOK_PUBLISHER);
            assertThat((int) response.path("pages")).isEqualTo(GIT_BOOK_PAGES);
        });
    }

    @Test
    @Story("Если книги нет в базе")
    @DisplayName("Проверка отсутствия книги по ISBN")
    void checkingBookNotFoundByIsbnTest() {
        Response response = step("Отправить запрос на получение книги", () ->
                given(baseReqSpec)
                        .queryParam("ISBN", incorrectRandomIsbn)
                        .get("/BookStore/v1/Book")
                        .then()
                        .spec(baseRespSpec(400))
                        .extract().response());

        step("Проверить ответ", () -> {
            assertThat(response.path("code").toString()).isEqualTo("1205");
            assertThat(response.path("message").toString())
                    .isEqualTo("ISBN supplied is not available in Books Collection!");
        });
    }
}
