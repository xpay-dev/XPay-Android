package com.xpayworld.payment.util

import java.text.DecimalFormat


fun formattedAmount(amount : String) : String {
    var formatedAmount = ""
    val len = amount.length
    val df = DecimalFormat("###,###,###.##")
    if (len in 1..8) {
        formatedAmount = df.format((amount.toFloat() / 100))
    } else if (len == 0) {
        formatedAmount = "0.00"
    }
    return formatedAmount
}