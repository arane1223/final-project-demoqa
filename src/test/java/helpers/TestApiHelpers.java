package helpers;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static specs.BookStoreSpecs.*;

public class TestApiHelpers {

    @Step("Сделать POST запрос")
    public static ValidatableResponse executePost(String path, Object body, int statusCode) {
        return given(baseReqSpec)
                .body(body)
                .when()
                .post(path)
                .then()
                .spec(baseRespSpec(statusCode));
    }

    @Step("Сделать POST запрос")
    public static ValidatableResponse executePost(String path, String token, Object body, int statusCode) {
        return given(baseReqSpec)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .post(path)
                .then()
                .spec(baseRespSpec(statusCode));
    }

    @Step("Сделать DELETE запрос")
    public static ValidatableResponse executeDelete(String path, String pathParam, String token, int statusCode) {
        return given(baseReqSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(path, pathParam)
                .then()
                .spec(baseRespSpec(statusCode));
    }

    @Step("Сделать DELETE запрос")
    public static ValidatableResponse executeDelete(String path, String pathParam, Object body, String token, int statusCode) {
        return given(baseReqSpec)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .delete(path, pathParam)
                .then()
                .spec(baseRespSpec(statusCode));
    }

    @Step("Сделать DELETE запрос")
    public static ValidatableResponse executeDelete(String path, String token, Object body, int statusCode) {
        return given(baseReqSpec)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .delete(path)
                .then()
                .spec(baseRespSpec(statusCode));
    }

    @Step("Сделать GET запрос")
    public static ValidatableResponse executeGet(String path, int statusCode) {
        return given(baseReqSpec)
                .get(path)
                .then()
                .spec(baseRespSpec(statusCode));
    }

    @Step("Сделать GET запрос")
    public static ValidatableResponse executeGet(String path, String queryParamName, String queryParam, int statusCode) {
        return given(baseReqSpec)
                .queryParam(queryParamName, queryParam)
                .get(path)
                .then()
                .spec(baseRespSpec(statusCode));
    }
}
