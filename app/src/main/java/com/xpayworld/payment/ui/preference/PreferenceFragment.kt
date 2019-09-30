package com.xpayworld.payment.ui.preference


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragment
import com.xpayworld.payment.ui.transaction.processTransaction.BaseDeviceFragment
import com.xpayworld.payment.util.SharedPrefStorage
import com.xpayworld.payment.util.WISE_PAD
import com.xpayworld.payment.util.WISE_POS
import kotlinx.android.synthetic.main.fragment_preference.*


class PreferenceFragment : BaseDeviceFragment() {


    override fun getLayout(): Int {
        return R.layout.fragment_preference
    }

    override fun initView(view: View, container: ViewGroup?) {
        switchSetUp()

        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = deviceListAdapter

        deviceListAdapter.onItemClick = { device ->
            SharedPrefStorage(context!!).writeMessage(WISE_PAD, device.name)
            bbDeviceController!!.connectBT(device)
        }

        swWisePad.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                startBluetoothConnection()
            } else {
                SharedPrefStorage(context!!).removeKey(WISE_PAD)
                stopConnection()
            }
        }

        swWisePos.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                SharedPrefStorage(context!!).writeMessage(WISE_POS, "true")
            } else {
                SharedPrefStorage(context!!).removeKey(WISE_POS)
            }
        }
    }

    private fun switchSetUp() {
        swWisePad.isChecked = !SharedPrefStorage(context!!).isEmpty(WISE_PAD)
        swWisePos.isChecked = !SharedPrefStorage(context!!).isEmpty(WISE_POS)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopConnection()
    }
}
