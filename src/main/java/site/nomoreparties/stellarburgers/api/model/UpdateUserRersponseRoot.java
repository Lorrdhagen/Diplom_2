package site.nomoreparties.stellarburgers.api.model;

public class UpdateUserRersponseRoot {
    private boolean success;
    private User user;

    public UpdateUserRersponseRoot(boolean success, User user) {
        this.success = success;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public User getUser() {
        return user;
    }
}
