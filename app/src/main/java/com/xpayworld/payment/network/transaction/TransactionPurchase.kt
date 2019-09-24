package com.xpayworld.payment.network.transaction

import com.google.gson.annotations.SerializedName
import com.xpayworld.payment.network.PosWsRequest
import com.xpayworld.payment.util.paymentType
import com.xpayworld.payment.util.posRequest
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.function.DoubleBinaryOperator


class TransactionPurchase(txn: Transaction) {


    @SerializedName("AccountTypeId")
    var accountType: Int? = AccountType.NONE.ordinal

    @SerializedName("Action")
    var action: Int? = Action.NONE.ordinal

    @SerializedName("DeviceId")
    var deviceId: Int? = 7

    @SerializedName("Device")
    var device: Int? = 7

    @SerializedName("LanguageUser")
    val languageUser = "EN-CA"

    @SerializedName("ProcessOffline")
    var processOffline = false

    @SerializedName("Tips")
    var tips: Double? = 0.0

    @SerializedName("CustomerEmail")
    var customerEmail: String = ""

    @SerializedName("POSWSRequest")
    var posWsRequest: PosWsRequest? = null

    @SerializedName("DeviceModelVersion")
    var deviceModelVersion: String? = ""

    @SerializedName("DeviceOsVersion")
    var deviceOsVersion: String? = ""

    @SerializedName("PosAppVersion")
    var posAppVersion: String? = ""

    @SerializedName("CardDetails")
    var cardInfo: CardInfo? = null


    init {

        val card = CardInfo()
        card.amount = txn.amount
        card.currency = txn.currency
        card.epb = txn.emvCard!!.epb
        card.emvICCData = txn.emvCard!!.emvICCData
        card.epbKsn = txn.emvCard!!.epbksn
        card.expMonth = txn.emvCard!!.expiryMonth
        card.expYear = txn.emvCard!!.expiryYear
        card.isFallback = txn.isFallback
        card.ksn = txn.emvCard!!.ksn
        card.merchantOrderId = txn.orderId
        card.nameOnCard = txn.emvCard!!.cardholderName
        card.track1 = txn.emvCard!!.encTrack1
        card.track2 = txn.emvCard!!.encTrack2
        card.track3 = txn.emvCard!!.encTrack3
        card.refNumberApp = posRequest!!.activationKey +""+ System.currentTimeMillis()
        cardInfo = card
        posWsRequest = posRequest

        when (val mPaymentType = paymentType) {
            is PaymentType.CREDIT -> {
                action = mPaymentType.action.ordinal
            }
            is PaymentType.DEBIT -> {
                accountType = mPaymentType.accountType.ordinal
            }
        }
    }


    enum class AccountType(val type: Int) {
        NONE(0), CHEQUING(1), SAVINGS(2), CURRENT(3)

    }

    enum class Action(val type: Int) {
        NONE(0), SWIPE(1), EMV(2)
    }

}