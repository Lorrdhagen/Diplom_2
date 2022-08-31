package site.nomoreparties.stellarburgers.api.model;

public class OrderListUnauthorized {
    private boolean success;
    private String message;
    private final String UNAUTH_MESSAGE = "You should be authorised";

    public OrderListUnauthorized(boolean success, String message) {
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

    @Override
    public String toString() {
        return "site.nomoreparties.stellarburgers.api.model.OrderListUnauthorized{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", UNAUTH_MESSAGE='" + UNAUTH_MESSAGE + '\'' +
                '}';
    }
}
