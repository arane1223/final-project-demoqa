package pages.components;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class RegistrationResultsComponent {
    private SelenideElement
            resultTitle = $("#example-modal-sizes-title-lg"),
            tableResponsive = $(".table-responsive");

    @Step("Проверить, что форма с результатом видна")
    public RegistrationResultsComponent checkFormVisible(String title){
        resultTitle.shouldBe(visible).shouldHave(text(title));
        tableResponsive.scrollTo();
        return this;
    }

    @Step("Проверить результат в таблице")
    public RegistrationResultsComponent checkFormResults (String key, String resultValue){
        tableResponsive.$(byText(key)).parent()
                .shouldHave(text(resultValue));
        return this;
    }

    @Step("Проверить результат в поле с картинкой")
    public RegistrationResultsComponent checkPictureResult (String key){
        tableResponsive.$(byText(key)).parent()
                .shouldNotBe(empty);
        return this;
    }

    @Step("Проверить, что форму с результатом не видно")
    public RegistrationResultsComponent checkFormInvisible(){
        resultTitle.shouldNotBe(visible);
        return this;
    }

    @Step("Проверить, дату рождения")
    public RegistrationResultsComponent checkDateOfBirth (String day, String month, String year){
        tableResponsive.$(byText("Date of Birth")).parent()
                .shouldHave(text(day+" "+month+","+year));
        return this;
    }
}