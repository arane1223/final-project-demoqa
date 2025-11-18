package data;

import config.AuthConfig;
import models.IsbnModel;
import models.LoginViewModel;
import org.aeonbits.owner.ConfigFactory;

import java.util.List;

import static utils.RandomUtils.*;

public class TestData {
    protected static AuthConfig authData = ConfigFactory.create(AuthConfig.class);
    protected static String userName = authData.userName();
    protected static String password = authData.password();

    public static final LoginViewModel AUTH_DATA = new LoginViewModel(userName, password);

    public static final List<String> BOOKS = List.of(
            "Git Pocket Guide",
            "Learning JavaScript Design Patterns",
            "Designing Evolvable Web APIs with ASP.NET",
            "Speaking JavaScript",
            "You Don't Know JS",
            "Programming JavaScript Applications",
            "Eloquent JavaScript, Second Edition",
            "Understanding ECMAScript 6");

    public static final String
            GIT_BOOK_ISBN = "9781449325862",
            GIT_BOOK_TITLE = "Git Pocket Guide",
            GIT_BOOK_SUB_TITLE = "A Working Introduction",
            GIT_BOOK_AUTHOR = "Richard E. Silverman",
            GIT_BOOK_PUBLISH_DATE = "2020-06-04T08:48:39.000Z",
            GIT_BOOK_PUBLISHER = "O'Reilly Media";

    public static String incorrectRandomIsbn = getRandomISBN();

    public static final int GIT_BOOK_PAGES = 234;

    public static final List<IsbnModel> BOOK_LIST = List.of(new IsbnModel(GIT_BOOK_ISBN));

    public static LoginViewModel randomAuthData() {
        String randomUserName = getRandomUserName();
        String randomPassword = getRandomPassword();
        return new LoginViewModel(randomUserName, randomPassword);
    }
}
