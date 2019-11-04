package com.xpayworld.payment.ui.transaction.receipt


import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragment
import kotlinx.android.synthetic.main.fragment_receipt.*
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.xpayworld.payment.databinding.FragmentReceiptBinding
import com.xpayworld.payment.network.TransactionResponse
import com.xpayworld.payment.ui.dashboard.DrawerLocker
import com.xpayworld.payment.ui.transaction.receipt.ReceiptFragmentArgs.fromBundle
import com.xpayworld.payment.util.InjectorUtil

class ReceiptFragment : BaseFragment() {

    private val viewModel: ReceiptViewModel by viewModels {
        InjectorUtil.provideReceiptViewModelFactory(requireContext())
    }

    val transaction by lazy {
        fromBundle(arguments!!).transaction
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentReceiptBinding.inflate(inflater, container, false)
        val gson = Gson()
         val data =  gson.fromJson(transaction, TransactionResponse::class.java)

        Log.e("error",transaction)
         binding.txns = data
        binding.lifecycleOwner = this@ReceiptFragment
        return binding.root
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

    override fun onResume() {
        super.onResume()
        (activity as DrawerLocker).drawerEnabled(false)
    }
}
