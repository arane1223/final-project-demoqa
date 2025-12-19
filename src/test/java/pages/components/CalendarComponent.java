package pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class CalendarComponent {
    private SelenideElement
            monthSelect = $(".react-datepicker__month-select"),
            yearSelect = $(".react-datepicker__year-select"),
            daySelect = $(".react-datepicker__month");

    public void setDate(String day, String month, String year) {
        monthSelect.$(byText(month)).click();
        yearSelect.$(byText(year)).click();
        daySelect.$(byText(day)).click();
    }
}
