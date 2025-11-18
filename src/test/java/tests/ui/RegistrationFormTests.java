package tests.ui;

import data.RegistrationData;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;

@Tags({@Tag("ui"), @Tag("registration")})
@Owner("sergeyglukhov")
@Link(value = "PracticeForm", url = "https://demoqa.com/automation-practice-form")
@Feature("Заполнение Practice Form")
@DisplayName("UI тесты на заполнение Practice Form на demoqa")
public class RegistrationFormTests extends TestBase {

    @Test
    @Story("Заполнение всех полей")
    @DisplayName("При заполнении всех полей Practice Form выйдет popup со значениями всех заполненных полей")
    void fullFillFormTest() {
        RegistrationData data = new RegistrationData();

        step("Открыть страницу и удалить рекламу", () ->
                registrationPage.openPage().deleteAdds());

        step("Заполнить все поля формы случайными значениями и жмем на кнопу Submit", () ->
                registrationPage
                        .setFirstName(data.firstName)
                        .setLastName(data.lastName)
                        .setEmail(data.userEmail)
                        .setGender(data.gender)
                        .setUserNumber(data.userNumber)
                        .setDayOfBirthday(data.birthDay, data.birthMonth, data.birthYear)
                        .setRandomSubjects(data.subjects)
                        .setHobbies(data.hobbies)
                        .setPicture(data.picture)
                        .setAddress(data.address)
                        .setStateAndCity(data.state, data.city)
                        .scrollToSubmit()
                        .clickOnSubmit());

        step("Проверить, что появился popup с введенными значениями во всех полях", () ->
                registrationResults
                        .checkFormVisible("Thanks for submitting the form")
                        .checkFormResults("Student Name", data.firstName + " " + data.lastName)
                        .checkFormResults("Student Email", data.userEmail)
                        .checkFormResults("Gender", data.gender)
                        .checkFormResults("Mobile", data.userNumber)
                        .checkDateOfBirth(data.birthDay, data.birthMonth, data.birthYear)
                        .checkFormResults("Subjects", data.subjects)
                        .checkFormResults("Hobbies", data.hobbies)
                        .checkPictureResult("Picture")
                        .checkFormResults("Address", data.address)
                        .checkFormResults("State and City", data.state + " " + data.city));
    }

    @Test
    @Story("Заполнение обязательных полей")
    @DisplayName("При заполнении обязательных полей Practice Form выйдет popup заполненными обязательными полями")
    void minimalFillFormTest() {
        RegistrationData data = new RegistrationData();

        step("Открыть страницу и удалить рекламу", () ->
                registrationPage.openPage().deleteAdds());

        step("Заполнить обязательные поля и жмем на кнопу Submit", () ->
                registrationPage
                        .setFirstName(data.firstName)
                        .setLastName(data.lastName)
                        .setGender(data.gender)
                        .setUserNumber(data.userNumber)
                        .scrollToSubmit()
                        .clickOnSubmit());

        step("Проверить, что появился popup с введенными значениями во всех полях", () ->
                registrationResults
                        .checkFormVisible("Thanks for submitting the form")
                        .checkFormResults("Student Name", data.firstName + " " + data.lastName)
                        .checkFormResults("Gender", data.gender)
                        .checkFormResults("Mobile", data.userNumber));
    }

    @Test
    @Story("Отправка пустой формы")
    @DisplayName("При нажатии на кнопку Submit с пустыми полями, popup не появится")
    void negativeFillFormTest() {
        registrationPage
                .openPage()
                .deleteAdds()
                .clickOnSubmit();

        registrationResults
                .checkFormInvisible();

    }
}