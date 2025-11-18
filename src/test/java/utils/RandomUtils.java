package utils;

import com.github.javafaker.Faker;

public class RandomUtils {

    public static Faker faker = new Faker();

    public static String getRandomFirstName() {
        return faker.name().firstName();
    }

    public static String getRandomLastName() {
        return faker.name().lastName();
    }

    public static String getRandomEmail() {
        return faker.internet().emailAddress();
    }

    public static String getRandomGender() {
        return faker.options().option("Male", "Female", "Other");
    }

    public static String getRandomNumber() {
        return faker.number().digits(10);
    }

    public static String getRandomBirthMonth() {
        String[] month = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        return faker.options().option(month);
    }

    public static String getRandomBirthDay(String month) {
        return switch (month) {
            case "January", "March", "May", "July", "August", "October", "December" ->
                    String.valueOf(faker.number().numberBetween(1, 31));
            case "April", "June", "September", "November" -> String.valueOf(faker.number().numberBetween(1, 30));
            case "February" -> String.valueOf(faker.number().numberBetween(1, 28));
            default -> throw new IllegalArgumentException(month);
        };
    }

    public static String getRandomBirthYear() {
        return String.valueOf(faker.number().numberBetween(1900, 2100));
    }

    public static String getRandomSubjects() {
        return faker.options().option("Maths", "Accounting", "Arts", "Social Studies", "Physics", "Chemistry",
                "Computer Science", "Commerce", "Economics", "Civics", "English", "Hindi", "Biology", "History");
    }

    public static String getRandomHobbies() {
        return faker.options().option("Sports", "Reading", "Music");
    }

    public static String getRandomPicture() {
        return faker.options().option(
                "jpeg/cyberpunk_1.jpeg",
                "jpeg/cyberpunk_2.jpeg",
                "jpeg/cyberpunk_3.jpeg",
                "jpeg/cyberpunk_4.jpeg");
    }

    public static String getRandomAddress() {
        return faker.address().streetAddress();
    }

    public static String getRandomSecondAddress() {
        return faker.address().streetAddress();
    }

    public static String getRandomState() {
        String[] state = {"NCR", "Uttar Pradesh", "Haryana", "Rajasthan"};
        return faker.options().option(state);
    }

    public static String getRandomCity(String state) {
        return switch (state) {
            case "NCR" -> faker.options().option("Delhi", "Gurgaon", "Noida");
            case "Uttar Pradesh" -> faker.options().option("Agra", "Lucknow", "Merrut");
            case "Haryana" -> faker.options().option("Karnal", "Panipat");
            case "Rajasthan" -> faker.options().option("Jaipur", "Jaiselmer");
            default -> throw new IllegalArgumentException(state);
        };
    }

    public static String getRandomUserName() {
        return faker.name().username();
    }


    public static String getRandomPassword() {
        return faker.internet().password(
                10, 40, true, true, true);
    }

    public static String getRandomISBN() {
        return faker.number().digits(12);
    }
}