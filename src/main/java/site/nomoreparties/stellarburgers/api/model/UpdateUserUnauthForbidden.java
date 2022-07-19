package site.nomoreparties.stellarburgers.api.model;

public class UpdateUserUnauthForbidden {
    private boolean success;
    private String message;
    private final String UNAUTH_MESSAGE = "You should be authorised";
    private final String FORBIDDEN_MESSAGE = "User with such email already exists";

    public UpdateUserUnauthForbidden(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getUnauthMessage() {
        return UNAUTH_MESSAGE;
    }

    public String getForbiddenMessage() {
        return FORBIDDEN_MESSAGE;
    }

    @Override
    public String toString() {
        return "site.nomoreparties.stellarburgers.api.model.UpdateUserUnauthForbidden{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", UNAUTH_MESSAGE='" + UNAUTH_MESSAGE + '\'' +
                ", FORBIDDEN_MESSAGE='" + FORBIDDEN_MESSAGE + '\'' +
                '}';
    }
}
