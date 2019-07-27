package com.xpayworld.payment.ui.transaction


import androidx.navigation.fragment.findNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragmentkt
import kotlinx.android.synthetic.main.fragment_process_transaction.*


class ProcessTransactionFragment : BaseFragmentkt() {

    override fun getLayout(): Int {
        return R.layout.fragment_process_transaction
    }

    override fun initView() {
        (activity as DrawerLocker).drawerEnabled(false)

        btnCancel.setOnClickListener {
            findNavController().navigateUp()
            (activity as DrawerLocker).drawerEnabled(true)
        }
    }


}
