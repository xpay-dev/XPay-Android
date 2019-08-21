package com.xpayworld.payment.ui.transaction

interface ToolbarDelegate {
    fun showToolbar(visible: Boolean)
    fun setTitle(title : String)
}
interface  IOnFocusListenable{
    fun onWindowFocusChanfed(hasFocus : Boolean)
}
interface DrawerLocker {
    fun drawerEnabled(enabled: Boolean)
}
