package com.xpayworld.payment.ui.transaction.processTransaction

import android.app.ActionBar
import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.xpayworld.payment.ui.base.kt.BaseActivity
import java.util.*
import kotlin.collections.HashMap


interface PipadDelegate {
    fun getActivity() : Activity
}


class ActivityPinPad : BaseDeviceActivity(), PipadDelegate {
    override fun getActivity(): Activity {
        return  this
    }

    private var wisePOSPlusPinPadView: WisePOSPlusPinPadView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
       currentActivity = this


        val decorView = getWindow().getDecorView()
        // Hide the status bar.
        val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar

                or View.SYSTEM_UI_FLAG_IMMERSIVE)
        decorView.setSystemUiVisibility(uiOptions)

        val actionBar = getActionBar()
        if (actionBar != null) {
            actionBar!!.hide()
        }
        wisePOSPlusPinPadView = WisePOSPlusPinPadView(applicationContext, pinButtonLayout)

        setContentView(wisePOSPlusPinPadView)
    }

    internal fun setStars(inputStars: String) {
        WisePOSPlusPinPadView.setStars(wisePOSPlusPinPadView!!, inputStars)
        wisePOSPlusPinPadView!!.invalidate()
    }
}
