package site.nomoreparties.stellarburgers.api.model;

public class LoginUserResponseRoot {
    private boolean success;
    private String accessToken;
    private String refreshToken;
    private User user;

    public LoginUserResponseRoot(boolean success, String accessToken, String refreshToken, User user) {
        this.success = success;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "site.nomoreparties.stellarburgers.api.model.LoginUserResponseRoot{" +
                "success=" + success +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", user=" + user +
                '}';
    }
}
