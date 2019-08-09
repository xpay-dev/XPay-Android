package com.xpayworld.payment.util

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.xpayworld.payment.R
import android.view.Gravity
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_error.*


class CustomDialog(context: Context) : DialogUI {

    var mDialog: Dialog? = null

    init {
        mDialog = Dialog(context)
        mDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog?.setCancelable(false)
        val v = mDialog?.window!!.decorView
        v.setBackgroundResource(android.R.color.transparent)

    }

//    override fun onSucess(): Dialog {
//
//    }

    override fun onError(): Dialog {

        mDialog!!.setContentView(R.layout.dialog_error)
        val title  = mDialog!!.findViewById<TextView>(R.id.tvTitle)
        val btnDone = mDialog!!.findViewById<Button>(R.id.btnDone)


        btnDone.setOnClickListener {
            mDialog!!.dismiss()
        }


//    val txtTitle = mDialog.findViewById<TextView>(R.id.dialog_error_title)
//    val txtMessage = mDialog.findViewById<TextView>(R.id.dialog_error_message)
//    val btnClose = mDialog.findViewById<Button>(R.id.dialog_error_button)
        return mDialog!!
    }
    fun onError(title : String): Dialog {

        mDialog!!.setContentView(R.layout.dialog_error)
        val tvTitle = mDialog!!.findViewById<TextView>(R.id.tvTitle)
        val btnDone = mDialog!!.findViewById<Button>(R.id.btnDone)
        tvTitle.text = title
        btnDone.setOnClickListener {
            mDialog!!.dismiss()
        }
    return  mDialog!!
    }

    override fun onDismiss() {
        mDialog?.dismiss()
    }

    override fun onLoading(): Dialog {
        mDialog!!.setContentView(R.layout.view_loading)
        val img = mDialog!!.findViewById<ImageView>(R.id.imgLoading)
        val anim = img?.drawable as AnimationDrawable
        anim.start()
        return mDialog!!
    }
}