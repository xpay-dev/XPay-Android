package com.xpayworld.payment.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.xpayworld.payment.R
import com.xpayworld.payment.databinding.ListItemHistoryBinding
import com.xpayworld.payment.network.transLookUp.TransResponse

class TransactionHistoryAdapter : RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>() {

    private lateinit var txnResponse: ArrayList<TransResponse>

    override fun getItemCount(): Int {
        return if (::txnResponse.isInitialized) txnResponse.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(txnResponse[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ListItemHistoryBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item_history, parent, false)
        return ViewHolder(binding)

    }

    fun updatePostList(txnList: ArrayList<TransResponse>) {
        this.txnResponse = txnList
        notifyDataSetChanged()
    }

    fun clear(){
        this.txnResponse.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ListItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        private val viewModel = HistoryViewModel()

        fun bind(txn: TransResponse) {
              viewModel.bind(txn)
            binding.viewModel = viewModel
        }
    }
}