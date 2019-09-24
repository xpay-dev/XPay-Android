package com.xpayworld.payment.ui.base.kt

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xpayworld.payment.ui.dialog.ErrorDialog
import com.xpayworld.payment.util.CustomDialog
import dagger.android.AndroidInjection

abstract  class BaseActivity : AppCompatActivity() ,BaseFragment.CallBack{
    private lateinit var dialog: CustomDialog
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        dialog = CustomDialog(applicationContext)
        initView()
    }

    fun showProgress() {
        runOnUiThread {
            dialog.onLoading().show()}
    }

    fun hideProgress() {
        runOnUiThread {
            dialog.onDismiss()}
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

    abstract fun initView()

    private fun performDI() = AndroidInjection.inject(this)


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        shouldSetToFullScreen()
    }

    fun shouldSetToFullScreen(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }
}