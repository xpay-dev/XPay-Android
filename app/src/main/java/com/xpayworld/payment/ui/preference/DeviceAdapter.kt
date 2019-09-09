package com.xpayworld.payment.ui.preference

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.xpayworld.payment.R
import com.xpayworld.payment.databinding.ListItemDeviceBinding

class DeviceAdapter : RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {

    private  lateinit var deviceList : List<Device>

    override fun getItemCount(): Int {
        return if(::deviceList.isInitialized) deviceList.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(deviceList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ListItemDeviceBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_item_device, parent, false)
        return ViewHolder(binding)

    }

    fun updatePostList(deviceList:List<Device>){
        this.deviceList = deviceList
        notifyDataSetChanged()
    }
    class ViewHolder(private val binding :  ListItemDeviceBinding): RecyclerView.ViewHolder(binding.root){

        private  val  viewModel = DeviceViewModel()

        fun bind(device: Device) {
            viewModel.bind(device)
            binding.viewModel = viewModel
        }
    }

}