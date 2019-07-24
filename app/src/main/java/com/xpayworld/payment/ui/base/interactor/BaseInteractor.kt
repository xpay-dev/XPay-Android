package com.xpayworld.payment.ui.base.interactor

open class  BaseInteractor(): MvpInteractor{
    override fun performUserLogout() {

    }

    override fun isUserLoggedIn(): Boolean {
        return  true
    }
}
