package com.xpayworld.payment.ui.base;



public interface MvpView {
    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(int resId);

    boolean isNetworkConnected();

    void hideKeyboard();
}
