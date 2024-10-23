package site.nomoreparties.stellarburgers.user;

import org.apache.commons.lang3.RandomStringUtils;

public class User {

    private final String email;
    private final String password;
    private final String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    static User random() {
        return new User("email" + RandomStringUtils.randomAlphanumeric(5, 15) + "@yandex.ru", "123QweЙцу!@#", "Имя");
    }

    static User emptyEmail() {
        return new User("", "password", "имя");
    }

    static User emptyPassword() {
        return new User("email", "", "имя");
    }

    static User emptyName() {
        return new User("email", "password", "");
    }

    static User emptyAllFields() {
        return new User("", "", "");
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }


}
