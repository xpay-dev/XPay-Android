package com.xpayworld.payment.ui.base.presenter

import com.xpayworld.payment.ui.base.interactor.MvpInteractor
import com.xpayworld.payment.ui.base.kt.MvpView
import com.xpayworld.payment.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter <V: MvpView, I : MvpInteractor>
internal constructor(protected  var interactor : I?,
                     protected val schedulerProvider: SchedulerProvider,
                     protected val compositeDisposable: CompositeDisposable)  : MvpPresenter<V, I>{

    private var view: V? = null
    private val isViewAttached: Boolean get() = view != null

    override fun onAttach(view: V?) {
        this.view = view
    }

    override fun getView(): V? = view

    override fun onDetach() {
        compositeDisposable.dispose()
        view = null
        interactor = null
    }
}