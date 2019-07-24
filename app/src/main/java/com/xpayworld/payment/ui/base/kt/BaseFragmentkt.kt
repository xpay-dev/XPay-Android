package com.xpayworld.payment.ui.base.kt

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.xpayworld.payment.ui.base.BaseActivity
import dagger.android.support.AndroidSupportInjection

abstract  class BaseFragmentkt : Fragment(), MvpViewkt {

    private var parentActivity: BaseActivity? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.parentActivity = activity
            activity?.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    override fun hideProgress() {

    }

    override fun showProgress() {

    }

    private fun performDependencyInjection() = AndroidSupportInjection.inject(this)

    fun getBaseActivity() = parentActivity

    interface CallBack {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }
    abstract fun setUp()

}