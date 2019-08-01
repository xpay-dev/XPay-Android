package com.xpayworld.payment.util

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.xpayworld.payment.R

class CustomDialog(context: Context) : DialogUI {

    var mDialog: Dialog = Dialog(context)

    init {
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog.setCancelable(false)
        val v = mDialog.window!!.decorView
        v.setBackgroundResource(android.R.color.transparent)

    }

//    override fun onSucess(): Dialog {
//
//    }

    override fun onError(): Dialog {
    mDialog.setContentView(R.layout.dialog_error)
//    val txtTitle = mDialog.findViewById<TextView>(R.id.dialog_error_title)
//    val txtMessage = mDialog.findViewById<TextView>(R.id.dialog_error_message)
//    val btnClose = mDialog.findViewById<Button>(R.id.dialog_error_button)
     return  mDialog
    }

    override fun onDismiss() {
        mDialog.dismiss()

    }

    override fun onLoading(): Dialog {
        mDialog.setContentView(R.layout.view_loading)
        val img = mDialog.findViewById<ImageView>(R.id.imgLoading)
        val anim = img?.drawable as AnimationDrawable
        anim.start()
        return mDialog
    }
}