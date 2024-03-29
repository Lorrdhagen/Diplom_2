package site.nomoreparties.stellarburgers.api.model;

import org.apache.commons.lang3.RandomStringUtils;

public class UserModel {
    private String email;
    private String password;
    private String name;

    public UserModel(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static UserModel getRandomUser() {
        String email = RandomStringUtils.randomAlphabetic(10) + "@test.ru";
        String password = RandomStringUtils.randomAlphabetic(10);
        String name = RandomStringUtils.randomAlphabetic(8);
        return new UserModel(email, password, name);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "site.nomoreparties.stellarburgers.api.model.UserModel{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
