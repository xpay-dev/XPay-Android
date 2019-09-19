package com.xpayworld.payment.ui.dialog

import androidx.fragment.app.DialogFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.app.AlertDialog
import android.view.LayoutInflater
import com.xpayworld.payment.R
import android.view.WindowManager
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.TextView


class ErrorDialog : DialogFragment() {

    private val ARG_TITLE = "AlertDialog.Title"
    private val ARG_MESSAGE = "AlertDialog.Message"

     fun showAlert(title: String, message: String, targetFragment: Fragment) {
        val dialog = ErrorDialog()
        val args = Bundle()
        args.putString(ARG_TITLE, title)
        args.putString(ARG_MESSAGE, message)
        dialog.arguments = args
        dialog.setTargetFragment(targetFragment, 0)
        targetFragment.fragmentManager?.let { dialog.show(it, "tag") }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_error, null)
        val alertDialog = AlertDialog.Builder(activity)
                .setView(mDialogView)
                .setCancelable(false)
                .create()

        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)

        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels

        // Temporarily set the dialogs window to not focusable to prevent the short
        // popup of the navigation bar.
        alertDialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        alertDialog.window!!.decorView.systemUiVisibility = activity!!.window.decorView.systemUiVisibility
        alertDialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        alertDialog.window!!.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
        alertDialog.window!!.attributes.width =  (width - (width * 0.25)).toInt()

        val btnDone = mDialogView.findViewById<Button>(R.id.btnDone)
        val title = mDialogView.findViewById<TextView>(R.id.tvTitle)
        val message =  mDialogView.findViewById<TextView>(R.id.tvMessage)

        title.text = arguments?.get(ARG_TITLE).toString()
        message.text = arguments?.get(ARG_MESSAGE).toString()

        btnDone.setOnClickListener {
            alertDialog.dismiss()
        }

        return alertDialog
    }

}