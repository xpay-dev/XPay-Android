package com.xpayworld.payment.ui.transaction.receipt

import android.content.Context
import com.xpayworld.payment.util.BaseViewModel
import java.io.ByteArrayOutputStream

class ReceiptViewModel(context: Context) : BaseViewModel(){
    private val INIT = byteArrayOf(0x1B, 0x40)
    private val POWER_ON = byteArrayOf(0x1B, 0x3D, 0x01)
    private val POWER_OFF = byteArrayOf(0x1B, 0x3D, 0x02)
    private val NEW_LINE = byteArrayOf(0x0A)
    private val ALIGN_LEFT = byteArrayOf(0x1B, 0x61, 0x00)
    private val ALIGN_CENTER = byteArrayOf(0x1B, 0x61, 0x01)
    private val ALIGN_RIGHT = byteArrayOf(0x1B, 0x61, 0x02)
    private val EMPHASIZE_ON = byteArrayOf(0x1B, 0x45, 0x01)
    private val EMPHASIZE_OFF = byteArrayOf(0x1B, 0x45, 0x00)
    private val FONT_5X8 = byteArrayOf(0x1B, 0x4D, 0x00)
    private val FONT_5X12 = byteArrayOf(0x1B, 0x4D, 0x01)
    private val FONT_8X12 = byteArrayOf(0x1B, 0x4D, 0x02)
    private val FONT_10X18 = byteArrayOf(0x1B, 0x4D, 0x03)
    private val FONT_SIZE_0 = byteArrayOf(0x1D, 0x21, 0x00)
    private val FONT_SIZE_1 = byteArrayOf(0x1D, 0x21, 0x11)
    private val CHAR_SPACING_0 = byteArrayOf(0x1B, 0x20, 0x00)
    private val CHAR_SPACING_1 = byteArrayOf(0x1B, 0x20, 0x01)
    private val KANJI_FONT_24X24 = byteArrayOf(0x1C, 0x28, 0x41, 0x02, 0x00, 0x30, 0x00)
    private val KANJI_FONT_16X16 = byteArrayOf(0x1C, 0x28, 0x41, 0x02, 0x00, 0x30, 0x01)

    fun genReceipt(context: Context): ByteArray {
        val baos = ByteArrayOutputStream()
        return baos.toByteArray()
    }


}