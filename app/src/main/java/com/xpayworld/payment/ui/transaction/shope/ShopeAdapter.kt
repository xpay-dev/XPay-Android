package com.xpayworld.payment.ui.transaction.shope

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.xpayworld.payment.R
import com.xpayworld.payment.databinding.ListItemCartBinding


class ShopeAdapter : RecyclerView.Adapter<ShopeAdapter.ViewHolder>() {

    lateinit var mShope: ArrayList<Shoppe>
    var onItemClick: ((Shoppe) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item_cart, parent, false))

    }

    override fun getItemCount(): Int {
        return  mShope.count()
    }

    override fun onBindViewHolder(holder:  ViewHolder, position: Int) {
        holder.bind(createOnClickListener(mShope[position]),mShope[position])
    }

    fun updatePostList(shopeArr: ArrayList<Shoppe>) {
        mShope = shopeArr
        notifyDataSetChanged()
    }

    private fun createOnClickListener(selectedItem : Shoppe) : View.OnClickListener{
        return View.OnClickListener {
            onItemClick?.invoke(selectedItem)
        }
    }

    class ViewHolder(private val binding: ListItemCartBinding) : RecyclerView.ViewHolder(binding.root) {

        private val mViewModel = ShopeAdapterViewModel()
        fun bind(listener: View.OnClickListener, shope: Shoppe) {
            mViewModel.bind(shope)
            binding.clickListener = listener
            binding.viewModel = mViewModel
        }
    }

}