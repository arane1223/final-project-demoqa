package tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.ConfigReader;
import config.ProjectConfig;
import config.WebConfig;
import helpers.Attach;
import helpers.auth.WithLoginExtension;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.ProfilePage;
import pages.RegistrationPage;
import pages.TextBoxPage;
import pages.components.RegistrationResultsComponent;
import pages.components.TextBoxResultsComponent;

@ExtendWith(WithLoginExtension.class)
public class TestBase {
    protected RegistrationPage registrationPage = new RegistrationPage();
    protected RegistrationResultsComponent registrationResults = new RegistrationResultsComponent();
    protected TextBoxPage textBox = new TextBoxPage();
    protected TextBoxResultsComponent textBoxResults = new TextBoxResultsComponent();
    protected ProfilePage profilePage = new ProfilePage();

    protected static final WebConfig webConfig = ConfigReader.Instance.read();

    @BeforeAll
    public static void setUp() {
        ProjectConfig projectConfig = new ProjectConfig(webConfig);
        projectConfig.webConfig();
        projectConfig.apiConfig();
    }

    @BeforeEach
    void addAllureListener() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            Attach.screenshotAs("Last screenshot");
            Attach.pageSource();
            Attach.browserConsoleLogs();
            Attach.addVideo();
            Selenide.closeWebDriver();
        }

    }
}
