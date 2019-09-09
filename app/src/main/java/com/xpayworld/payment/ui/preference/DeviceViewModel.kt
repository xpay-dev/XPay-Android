package com.xpayworld.payment.ui.preference

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class  DeviceViewModel : ViewModel(){
    private val deviceName = MutableLiveData<String>()
    private val deviceId =  MutableLiveData<Int>()
    private val isActive = MutableLiveData<Boolean>()


    fun bind (device : Device){
        deviceName.value = device.name
        deviceId.value = device.id
    }

    fun  getDeviceName(): MutableLiveData<String>{
        return deviceName
    }

    fun  getDeviceId(): MutableLiveData<Int>{
        return deviceId
    }
}