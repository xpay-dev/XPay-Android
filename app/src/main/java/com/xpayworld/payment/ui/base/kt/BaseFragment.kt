package com.xpayworld.payment.ui.base.kt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.xpayworld.payment.util.CustomDialog
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment(), MvpView {

    private var parentActivity: BaseActivity? = null
    private lateinit var dialog: CustomDialog
    var mContainer : ViewGroup ? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(getLayout(), container, false)
        mContainer = container
        return  view
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view , mContainer)
        dialog = CustomDialog(context!!)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.parentActivity = activity

            activity?.onFragmentAttached()
        }
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
            CustomDialog(this.context!!).onError().show()
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
    abstract fun initView(view: View , container: ViewGroup?)

}