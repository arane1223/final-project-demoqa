package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class TextBoxPage {
    private SelenideElement
            userNameInput = $("#userName"),
            userEmailInput = $("#userEmail"),
            currentAddressInput = $("#currentAddress"),
            permanentAddressInput = $("#permanentAddress"),
            submitInput = $("#submit");

    @Step("Открыть страницу Text Box")
    public TextBoxPage openPage() {
        open("/text-box");
        return this;
    }

    @Step("Удалить рекламу со страницы")
    public TextBoxPage deleteAdds() {
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");
        return this;
    }

    @Step("Заполнить поле с именем")
    public TextBoxPage setUserName(String name) {
        userNameInput.setValue(name);
        return this;
    }

    @Step("Заполнить поле с e-mail")
    public TextBoxPage setUserEmail(String email) {
        userEmailInput.setValue(email);
        return this;
    }

    @Step("Заполнить поля с адресами")
    public TextBoxPage setAllAddresses(String current, String permanent) {
        currentAddressInput.setValue(current);
        permanentAddressInput.setValue(permanent);
        return this;
    }

    @Step("Заполнить поля с адресами")
    public TextBoxPage setAllAddresses(List<String> address) {
        currentAddressInput.setValue(address.get(0));
        permanentAddressInput.setValue(address.get(1));
        return this;
    }

    @Step("Кликнуть на кнопку «Submit»")
    public TextBoxPage clickOnSubmit() {
        submitInput.click();
        return this;
    }

    @Step("Проскролить до кнопки «Submit»")
    public TextBoxPage scrollToSubmit() {
        submitInput.scrollTo();
        return this;
    }
}
