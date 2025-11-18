package models;

import lombok.Data;

import java.util.List;

@Data
public class CreateUserResultModel {
    String userID, username;
    List<BookModel> books;
}
