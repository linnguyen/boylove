package com.example.lin.dollar.activity.Login;

/**
 * Created by lin on 18/09/2017.
 */

public interface LoginView {
    void showProgress();

    void showMessage(String message);

    void hideProgress();

    void navigateToHome();
}
