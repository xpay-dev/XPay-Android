package com.xpayworld.payment.ui.transaction.receipt


import android.view.*
import android.view.animation.AnimationUtils
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragment
import kotlinx.android.synthetic.main.fragment_receipt.*
import androidx.navigation.findNavController
import com.xpayworld.payment.ui.transaction.DrawerLocker

class ReceiptFragment : BaseFragment() {
    private fun bindView() {

    }

    override fun getLayout(): Int {
        return R.layout.fragment_receipt
    }

    override fun initView(view: View , container: ViewGroup?) {
        setHasOptionsMenu(true)
        btnDone.setOnClickListener {
            clReceipt.startAnimation(AnimationUtils.loadAnimation(context!!, R.anim.receipt_out))

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val inflater = activity?.menuInflater
        inflater?.inflate(R.menu.menu_receipt, menu)
        super.onCreateOptionsMenu(menu, inflater!!)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.receipt_item_done -> {
                val direction = ReceiptFragmentDirections.actionReceiptFragmentToTransactionFragment("")
                 view!!.findNavController().navigate(direction)
                (activity as DrawerLocker).drawerEnabled(true)
            }
        }
        return false
    }
}
