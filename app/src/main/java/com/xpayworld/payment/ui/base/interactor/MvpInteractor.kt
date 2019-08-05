package com.xpayworld.payment.ui.base.interactor

interface MvpInteractor {
    fun isUserLoggedIn(): Boolean

    fun performUserLogout()
}