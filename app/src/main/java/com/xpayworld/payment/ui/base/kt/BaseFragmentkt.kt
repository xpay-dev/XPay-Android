package com.xpayworld.payment.ui.base.kt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.xpayworld.payment.ui.base.BaseActivity
import com.xpayworld.payment.util.CustomDialog
import dagger.android.support.AndroidSupportInjection
import kotlin.concurrent.thread

abstract class BaseFragmentkt : Fragment(), MvpViewkt {

    private var parentActivity: BaseActivitykt? = null
    private lateinit var dialog: CustomDialog
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context as BaseActivitykt?
            this.parentActivity = activity
            activity?.onFragmentAttached()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), container, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog = CustomDialog(context!!)
        setHasOptionsMenu(false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)

    }

    override fun hideProgress() {
        activity?.runOnUiThread {
            dialog.onDismiss()
        }
    }

    override fun showProgress() {
        activity?.runOnUiThread {
            dialog.onLoading().show()
        }
    }

    override fun showNetworkError() {
        activity?.runOnUiThread {
            dialog.onError().show()
        }
    }


    private fun performDependencyInjection() = AndroidSupportInjection.inject(this)

    fun getBaseActivity() = parentActivity

    interface CallBack {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }

    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun initView(view: View)

}