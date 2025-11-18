package pages.components;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class TextBoxResultsComponent {
    protected SelenideElement
            nameCheck = $("#output #name"),
            emailCheck = $("#output #email"),
            currentAddressCheck = $("#output #currentAddress"),
            permanentAddressCheck = $("#output #permanentAddress");

    @Step("Проверить результат после заполнения формы")
    public TextBoxResultsComponent checkResults(String name, String email,
                                            String currentAddress, String permanentAddress) {
        nameCheck.shouldHave(text(name));
        emailCheck.shouldHave(text(email));
        currentAddressCheck.shouldHave(text(currentAddress));
        permanentAddressCheck.shouldHave(text(permanentAddress));
        return this;
    }

    @Step("Проверить результат после заполнения формы")
    public TextBoxResultsComponent checkResults(String name, String email,
                                            List<String> address) {
        nameCheck.shouldHave(text(name));
        emailCheck.shouldHave(text(email));
        currentAddressCheck.shouldHave(text(address.get(0)));
        permanentAddressCheck.shouldHave(text(address.get(1)));
        return this;
    }
}