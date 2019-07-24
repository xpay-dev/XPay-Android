package com.xpayworld.payment.ui.base.presenter

import com.xpayworld.payment.ui.base.interactor.MvpInteractor
import com.xpayworld.payment.ui.base.kt.MvpViewkt
import com.xpayworld.payment.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable


interface MvpPresenterkt<V : MvpViewkt, I : MvpInteractor> {

    fun onAttach(view: V?)

    fun onDetach()

    fun getView(): V?

}