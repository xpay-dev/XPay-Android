package com.xpayworld.payment.ui.base.presenter

import com.xpayworld.payment.ui.base.interactor.MvpInteractor
import com.xpayworld.payment.ui.base.kt.MvpView


interface MvpPresenter<V : MvpView, I : MvpInteractor> {

    fun onAttach(view: V?)

    fun onDetach()

    fun getView(): V?

}