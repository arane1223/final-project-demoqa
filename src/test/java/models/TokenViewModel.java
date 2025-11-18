package models;

import lombok.Data;

@Data
public class TokenViewModel {
    String token, expires, status, result;
}
