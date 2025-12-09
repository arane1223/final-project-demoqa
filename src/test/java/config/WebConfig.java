package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:local.properties",
        "classpath:remote.properties",
        "system:properties",
        "system:env"
})
public interface WebConfig extends Config {

    @Key("browser")
    String browser();

    @Key("browserVersion")
    String browserVersion();

    @Key("browserSize")
    String browserSize();

    @Key("baseUrl")
    String baseUrl();

    @Key("remoteUrl")
    @DefaultValue("")
    String remoteUrl();

    @Key("pageLoadStrategy")
    String pageLoadStrategy();
}
