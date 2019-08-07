package com.xpayworld.payment.ui.transaction


import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils

import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragmentkt
import kotlinx.android.synthetic.main.fragment_receipt.*
import android.os.Bundle
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_enter_amount.*


class ReceiptFragment : BaseFragmentkt() {
    override fun getLayout(): Int {
        return R.layout.fragment_receipt
    }


    override fun initView(view: View) {
        setHasOptionsMenu(true)
        btnDone.setOnClickListener {
            clReceipt1.startAnimation(AnimationUtils.loadAnimation(context!!, R.anim.receipt_out))

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // TODO Add your menu entries here
        val inflater = activity?.menuInflater
        inflater?.inflate(R.menu.menu_receipt, menu)
        super.onCreateOptionsMenu(menu, inflater!!)
    }
    private fun navigateToTransaction(){

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.receipt_item_done -> {
             view!!.findNavController().navigate(R.id.action_receiptFragment_to_transactionFragment)
                (activity as DrawerLocker).drawerEnabled(true)
            }
        }

        return false
    }
}
