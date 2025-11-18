package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.components.CalendarComponent;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationPage {
    private SelenideElement
            userForm = $("#userForm"),
            firstNameInput = $("#firstName"),
            lastNameInput = $("#lastName"),
            userEmailInput = $("#userEmail"),
            genderWrapper = $("#genterWrapper"),
            userNumberInput = $("#userNumber"),
            celendarInput = $("#dateOfBirthInput"),
            subjectInput = $("#subjectsInput"),
            hobbiesWrapper = $("#hobbiesWrapper"),
            uploadPicture = $("#uploadPicture"),
            adressInput = $("#currentAddress"),
            stateInput = $("#react-select-3-input"),
            cityInput = $("#react-select-4-input"),
            submitInput = $("#submit");

    @Step("Открыть страницу регистрации")
    public RegistrationPage openPage() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        return this;
    }

    @Step("Удалить рекламу со страницы")
    public RegistrationPage deleteAdds(){
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");
        return this;
    }

    @Step("Ввести имя")
    public RegistrationPage setFirstName(String value){
        firstNameInput.setValue(value);
        return this;
    }

    @Step("Ввести фамилию")
    public RegistrationPage setLastName(String value){
        lastNameInput.setValue(value);
        return this;
    }

    @Step("Ввести e-mail")
    public RegistrationPage setEmail(String email){
        userEmailInput.setValue(email);
        return this;
    }

    @Step("Выбрать пол")
    public RegistrationPage setGender(String gender){
        genderWrapper.$(byText(gender)).click();
        return this;
    }

    @Step("Ввести номер телефона")
    public RegistrationPage setUserNumber(String number){
        userNumberInput.setValue(number);
        return this;
    }

    @Step("Заполнить дату рождения")
    public RegistrationPage setDayOfBirthday(String day, String month, String year){
        celendarInput.click();
        CalendarComponent celendarComponent = new CalendarComponent();
        celendarComponent.setDate(day, month, year);
        return this;
    }

    @Step("Выбрать случайные предметы")
    public RegistrationPage setRandomSubjects(String value) {
        if (value == null || value.isBlank()) return this;

        for (String raw : value.split(",")) {
            String label = raw.trim();
            if (label.isEmpty()) continue;

            userForm.scrollTo();
            subjectInput.setValue(label);
            subjectInput.pressEnter();
        }
        return this;
    }

    @Step("Выбрать хобби")
    public RegistrationPage setHobbies(String value) {
        String[] hobbies = value.split(",");
        for (String hobby : hobbies) {
            hobbiesWrapper.$(byText(hobby.trim())).click();
        }
        return this;
    }

    @Step("Загрузить картинку")
    public RegistrationPage setPicture(String fileName){
        uploadPicture.uploadFromClasspath(fileName);
        return this;
    }

    @Step("Ввести адрес")
    public RegistrationPage setAddress(String address){
        adressInput.setValue(address);
        return this;
    }

    @Step("Выбрать штат и город")
    public RegistrationPage setStateAndCity (String state, String city){

        stateInput.setValue(state).pressEnter();
        cityInput.setValue(city).pressEnter();
        return this;
    }

    @Step("Кликнуть на кнопку «Submit»")
    public RegistrationPage clickOnSubmit(){
        submitInput.click();
        return this;
    }
}
