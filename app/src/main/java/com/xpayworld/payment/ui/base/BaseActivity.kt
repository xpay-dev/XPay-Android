package com.xpayworld.payment.ui.base.kt

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xpayworld.payment.ui.dialog.ErrorDialog
import com.xpayworld.payment.util.CustomDialog
import dagger.android.AndroidInjection
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import androidx.navigation.findNavController
import com.xpayworld.payment.R

abstract  class BaseActivity : AppCompatActivity() ,BaseFragment.CallBack{
    private lateinit var dialog: CustomDialog
    var handler: Handler? = null
    var r: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        dialog = CustomDialog(applicationContext)
        initView()

        handler = Handler()
        r = Runnable {
            println("Running")
            supportFragmentManager.primaryNavigationFragment?.let {
                ErrorDialog().showAlert(
                        "Session Time out",
                        "Sorry , your session timed out after a long time of inactivity, Please click DONE and Log in again",
                        {
                            findNavController(R.id.nav_host_fragment).navigate(R.id.logoutFragment)
                        },
                        it)
            }

        }
        startHandler()
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

    override fun onUserInteraction() {
        super.onUserInteraction()
        stopHandler()
        startHandler()
    }
    fun stopHandler() {
        handler?.removeCallbacks(r)
    }

    fun startHandler() {
        handler?.postDelayed(r, (5 * 60 * 1000).toLong()) //for 5 minutes
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