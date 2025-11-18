package tests.ui;

import data.RegistrationData;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tests.TestBase;

import java.util.List;
import java.util.stream.Stream;

import static io.qameta.allure.Allure.step;

@Tags({@Tag("ui"), @Tag("box")})
@Owner("sergeyglukhov")
@Link(value = "TextBox", url = "https://demoqa.com/text-box")
@Feature("Заполнение Text Box")
@DisplayName("UI тесты на заполнение Text Box на demoqa")
public class TextBoxTests extends TestBase {

    @Test
    @Story("Заполнение всех полей с помощью библиотеки Faker")
    @DisplayName("Успешное заполнении формы Text Box с генерацией данных через Faker")
    void successfulFillingFormWithFakerTest() {
        RegistrationData data = new RegistrationData();

        step("Открыть страницу и удалить рекламу", () ->
                textBox.openPage().deleteAdds());

        step("Заполнить форму и жмем на кнопку Submit", () ->
                textBox
                        .setUserName(data.firstName)
                        .setUserEmail(data.userEmail)
                        .setAllAddresses(data.address, data.secondAddress)
                        .scrollToSubmit()
                        .clickOnSubmit());

        step("Проверить, что вывелись такие же данные, которые были введены", () ->
                textBoxResults.checkResults(data.firstName, data.userEmail, data.address, data.secondAddress));
    }

    static Stream<Arguments> fillingFormWithMethodSourceParametrizeTest() {
        return Stream.of(
                Arguments.of(
                        "Alex",
                        "alex@egorov.com",
                        List.of("Some street 1", "Another street 1")),
                Arguments.of(
                        "Bob",
                        "Bob@gmail.com",
                        List.of("London", "Baker street 231"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Заполнение формы с именем {0}, почтой {1}, адресами {2}")
    @Story("Заполнение всех полей с помощью @MethodSource")
    @DisplayName("Заполнение формы Text Box с получением данных с помощью @MethodSource")
    void fillingFormWithMethodSourceParametrizeTest(String userName, String userEmail,
                                                    List<String> addresses) {
        step("Открыть страницу и удаляем рекламу", () ->
                textBox.openPage().deleteAdds());

        step("Ввести данные с именем, почтой, адресами, и жмем на Submit", () ->
                textBox
                        .setUserName(userName)
                        .setUserEmail(userEmail)
                        .setAllAddresses(addresses)
                        .scrollToSubmit()
                        .clickOnSubmit());

        step("Проверить, что появилось поле с такими же: именем, почтой, адресами", () ->
                textBoxResults.checkResults(userName, userEmail, addresses));
    }
}