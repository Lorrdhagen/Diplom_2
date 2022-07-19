package site.nomoreparties.stellarburgers.api.model;

public class CreateUserForbidden {
    private boolean success;
    private String message;
    private final String USER_EXISTS = "User already exists";
    private final String REQUIRED_FIELDS = "Email, password and name are required fields";

    public CreateUserForbidden(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getUserExists() {
        return USER_EXISTS;
    }

    public String getRequiredFields() {
        return REQUIRED_FIELDS;
    }

    @Override
    public String toString() {
        return "site.nomoreparties.stellarburgers.api.model.CreateUserForbidden{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", USER_EXISTS='" + USER_EXISTS + '\'' +
                ", REQUIRED_FIELDS='" + REQUIRED_FIELDS + '\'' +
                '}';
    }
}
