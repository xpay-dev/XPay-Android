package com.xpayworld.payment.ui.transaction.processTransaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.xpayworld.payment.ui.transaction.DrawerLocker

class PinPadFragment : BaseDeviceFragment() {

    private var wisePOSPlusPinPadView: WisePOSPlusPinPadView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        wisePOSPlusPinPadView = WisePOSPlusPinPadView(context!!, pinButtonLayout)
        return wisePOSPlusPinPadView
    }

    internal fun setStars(inputStars: String) {
        WisePOSPlusPinPadView.setStars(wisePOSPlusPinPadView!!, inputStars)
        wisePOSPlusPinPadView!!.invalidate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.hide()
        (activity as DrawerLocker).drawerEnabled(true)
    }
}