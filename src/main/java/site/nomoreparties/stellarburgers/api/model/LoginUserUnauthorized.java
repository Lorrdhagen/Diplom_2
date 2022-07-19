package site.nomoreparties.stellarburgers.api.model;

public class LoginUserUnauthorized {
    private boolean success;
    private String message;
    private final String INCORRECT_CREDENTIALS = "email or password are incorrect";

    public LoginUserUnauthorized(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getIncorrectCredentials() {
        return INCORRECT_CREDENTIALS;
    }

    @Override
    public String toString() {
        return "site.nomoreparties.stellarburgers.api.model.LoginUserUnauthorized{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", INCORRECT_CREDENTIALS='" + INCORRECT_CREDENTIALS + '\'' +
                '}';
    }
}
