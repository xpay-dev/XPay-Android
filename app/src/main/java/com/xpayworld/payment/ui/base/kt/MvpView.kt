package com.xpayworld.payment.ui.base.kt

interface MvpView {

    fun showProgress()
    fun showNetworkError()
    fun hideProgress()
}