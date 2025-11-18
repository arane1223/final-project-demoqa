package data;

import static utils.RandomUtils.*;

public class RegistrationData {
    public String
            firstName = getRandomFirstName(),
            lastName = getRandomLastName(),
            userEmail = getRandomEmail(),
            gender = getRandomGender(),
            userNumber = getRandomNumber(),
            birthDay = getRandomBirthDay(getRandomBirthMonth()),
            birthMonth = getRandomBirthMonth(),
            birthYear = getRandomBirthYear(),
            subjects = getRandomSubjects(),
            hobbies = getRandomHobbies(),
            picture = getRandomPicture(),
            address = getRandomAddress(),
            secondAddress = getRandomSecondAddress(),
            state = getRandomState(),
            city = getRandomCity(state);
}
