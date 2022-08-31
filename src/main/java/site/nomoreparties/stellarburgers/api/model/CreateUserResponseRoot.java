package site.nomoreparties.stellarburgers.api.model;

public class CreateUserResponseRoot {
    private boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;

    public CreateUserResponseRoot(boolean success, User createUserResponse, String accessToken, String refreshToken) {
        this.success = success;
        this.user = createUserResponse;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public boolean isSuccess() {
        return success;
    }

    public User getUser() {
        return user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public String toString() {
        return "site.nomoreparties.stellarburgers.api.model.CreateUserResponseRoot{" +
                "success=" + success +
                ", user=" + user +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
