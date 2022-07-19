package site.nomoreparties.stellarburgers.api.model;

public class OrderErrorsResponse {
    private boolean success;
    private String message;
    private final String UNAUTH_MESSAGE = "You should be authorised";
    private final String BAD_REQUEST_MESSAGE = "Ingredient ids must be provided";

    public OrderErrorsResponse(boolean success, String message) {
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

    public String getBadRequestMessage() {
        return BAD_REQUEST_MESSAGE;
    }

    @Override
    public String toString() {
        return "site.nomoreparties.stellarburgers.api.model.OrderErrorsResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", UNAUTH_MESSAGE='" + UNAUTH_MESSAGE + '\'' +
                ", BAD_REQUEST_MESSAGE='" + BAD_REQUEST_MESSAGE + '\'' +
                '}';
    }
}
