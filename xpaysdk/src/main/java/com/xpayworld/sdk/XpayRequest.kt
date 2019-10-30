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
    var entryPoint =  EntryPoint.TRANSACTION.value

}

enum class EntryPoint(val value: String) {
    TRANSACTION("transaction_page"),
    HISTORY("history_page"),
    ENTER_PIN("enter_pin_page"),
    PREFERENCE("preference_page"),
    ACTIVATION("activation_page")
}

enum class  TransactionType(val value: Int){
    CREDIT(0),DEBIT(1)
}