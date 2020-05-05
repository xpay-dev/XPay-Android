package com.xpayworld.payment.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.xpayworld.payment.network.PosWsRequest
import com.xpayworld.payment.network.TransactionResponse
import com.xpayworld.payment.network.transaction.PaymentType
import com.xpayworld.payment.network.transaction.Transaction
import com.xpayworld.payment.network.updateApp.UpdateAppResponse
import java.security.MessageDigest
import java.text.DecimalFormat


// Global Variables

var paymentType : PaymentType? = null
var POS_REQUEST  : PosWsRequest? = null
var transactionResponse : TransactionResponse? = null
var transaction =  Transaction()
var merchantDetails = UpdateAppResponse().merchantDetails
var IS_SDK = false
var IS_TRANSACTION_OFFLINE = false
var externalPackageName = ""
var SDK_XPAY_RESPONSE = ""


fun formattedAmount(amount : String) : String {
    var formatedAmount = ""

    if (amount == "" || amount.run { isNullOrBlank() }) {return  "0.00"}

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

@SuppressLint("HardwareIds")
fun getDeviceIMEI(activity: Activity): String? {
    var deviceUniqueIdentifier: String? = null
    val tm = activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
         //   imei = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        }
    } else {
      //  imei = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }
    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_PHONE_STATE), 1)
    else
//        deviceUniqueIdentifier = tm.deviceId
    if (null == deviceUniqueIdentifier || deviceUniqueIdentifier.isEmpty())
        deviceUniqueIdentifier = "0"
    return deviceUniqueIdentifier
}

fun sha256(base: String): String? {
    return try {
        val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
        val hash: ByteArray = digest.digest(base.toByteArray(charset("UTF-8")))
        val hexString = StringBuffer()
        for (i in hash.indices) {
            val hex = Integer.toHexString(0xff and hash[i].toInt())
            if (hex.length == 1) hexString.append('0')
            hexString.append(hex)
        }
        hexString.toString()
    } catch (ex: Exception) {
        throw RuntimeException(ex)
    }
}

fun getEncryptedSharedPreferences(context: Context): SharedPreferences? {
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    return EncryptedSharedPreferences.create(
            "secret_shared_prefs_file",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

fun isProbablyAnEmulator() = Build.FINGERPRINT.startsWith("generic")
        || Build.FINGERPRINT.startsWith("unknown")
        || Build.MODEL.contains("google_sdk")
        || Build.MODEL.contains("Emulator")
        || Build.MODEL.contains("Android SDK built for x86")
        || Build.BOARD == "QC_Reference_Phone" //bluestacks
        || Build.MANUFACTURER.contains("Genymotion")
        || Build.HOST.startsWith("Build") //MSI App Player
        || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
        || "google_sdk" == Build.PRODUCT