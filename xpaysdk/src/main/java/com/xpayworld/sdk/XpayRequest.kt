package com.xpayworld.sdk

import com.google.gson.annotations.SerializedName

class  XpayRequest {

    @SerializedName("merchant_name")
    var merchantName = ""

    @SerializedName("transaction_type")
    var txnType = TransactionType.CREDIT.value

    @SerializedName("transaction_id")
    var txnId = ""

    @SerializedName("card_capture_method")
    var cardCaptureMethod = ""

    @SerializedName("amount_purchase")
    var amountPurchase = 0.00

    @SerializedName("staff_id")
    var staffId = ""

    @SerializedName("app_package_name")
    var appPackageName = ""

    @SerializedName("transaction_currency")
    var currency= ""

    @SerializedName("transaction_entry_point")
    var entryPoint =  EntryPoint.TRANSACTION.toString()

}

enum class EntryPoint(val value: String) {
    TRANSACTION("TRANSACTION"),
    HISTORY("HISTORY"),
    ENTER_PIN("ENTER_PIN"),
    PREFERENCE("PREFERENCE"),
    ACTIVATION("ACTIVATION");
    override fun toString() = value
}

enum class  TransactionType(val value: Int){
    CREDIT(0),DEBIT(1)
}