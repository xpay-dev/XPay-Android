package com.xpayworld.payment.util

import java.text.DecimalFormat

fun formattedAmount(amount : String) : String {
    var formatedAmount = ""

    if (amount == "") {return  "0.00"}

    val len = amount.length
    val df = DecimalFormat("###,###,##0.00")
    if (len in 1..8) {
        val s = String.format("%6.2f", amount.toInt() / 100.0)
        formatedAmount = df.format(s.toDouble())
    } else if (len == 0) {
        formatedAmount = "0.00"
    }
    return formatedAmount
}