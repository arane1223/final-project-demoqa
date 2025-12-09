package config;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.openqa.selenium.MutableCapabilities;

import java.util.Map;

public class ProjectConfig {
    private final WebConfig webConfig;

    public ProjectConfig(WebConfig webConfig) {
        this.webConfig = webConfig;
    }

    public void apiConfig() {
        RestAssured.baseURI = webConfig.baseUrl();
    }

    public void webConfig() {
        Configuration.baseUrl = System.getProperty("baseUrl", webConfig.baseUrl());
        Configuration.browser = System.getProperty("browser", webConfig.browser());
        Configuration.browserVersion = System.getProperty("browserVersion", webConfig.browserVersion());
        Configuration.browserSize = System.getProperty("browserSize", webConfig.browserSize());
        Configuration.pageLoadStrategy = webConfig.pageLoadStrategy();

        String remoteUrl = webConfig.remoteUrl();
        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            Configuration.remote = remoteUrl;
            Configuration.browserCapabilities = new MutableCapabilities();
            Configuration.browserCapabilities.setCapability("selenoid:options",
                    Map.<String, Object>of(
                            "enableVNC", true,
                            "enableVideo", true
                    )
            );
        }
    }

}
