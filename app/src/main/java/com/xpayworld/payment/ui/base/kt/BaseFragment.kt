package com.xpayworld.payment.ui.base.kt

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.dialog.ErrorDialog
import com.xpayworld.payment.util.CustomDialog
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment() {

    private var parentActivity: BaseActivity? = null
    private lateinit var dialog: CustomDialog
    var mContainer: ViewGroup? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = if (getLayout() is Int) {
            inflater.inflate((getLayout() as Int), container, false)
        } else {
            getLayout() as View
        }

        mContainer = container
        return view
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, mContainer)
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

    fun hideProgress() {
        activity?.runOnUiThread {
            dialog.onDismiss()
        }
    }

    fun showProgress() {
            activity?.runOnUiThread {
                dialog.onLoading().show()
            }
    }

    fun showNetworkError(title: String? = null, message: String? = null, callBack: (() -> Unit)? = null) {
        val dialog = ErrorDialog().showAlert(
                title ?: "Network",
                message
                        ?: "Looks lie we weren't able to connect to our server. Please check your connection and try again",
                callBack,
                this)
    }


    private fun performDependencyInjection() = AndroidSupportInjection.inject(this)

    fun getBaseActivity() = parentActivity

    interface CallBack {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }

    abstract fun getLayout(): Any
    abstract fun initView(view: View, container: ViewGroup?)


}