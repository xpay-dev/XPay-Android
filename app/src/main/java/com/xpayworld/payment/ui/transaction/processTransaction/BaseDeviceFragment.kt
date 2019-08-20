package com.xpayworld.payment.ui.transaction.processTransaction

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.bbpos.bbdevice.BBDeviceController
import com.bbpos.bbdevice.CAPK
import com.bbpos.bbdevice.ota.BBDeviceOTAController
import com.xpayworld.payment.R
import java.text.SimpleDateFormat
import java.util.*


open class BaseDeviceFragment : Fragment(){

    companion object {

        lateinit var pinButtonLayout: Hashtable<String, Rect>
    }

    internal var bbDeviceController: BBDeviceController? = null
    internal lateinit var otaController: BBDeviceOTAController
     private var listener: MyBBdeviceControllerListener? = null

    val toolbarTitle: MutableLiveData<String> = MutableLiveData()
    val startAnimation: MutableLiveData<Boolean> = MutableLiveData()
    val strAmount : MutableLiveData<String> = MutableLiveData()
    val onResult : MutableLiveData<Boolean> = MutableLiveData()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//
//        val f = activity!!.fragmentManager.findFragmentById(R.id.processTransactionLayout)
//
//        if (f is ProcessTransaction) {
//
//            print("this is process transaction fragment")
//        }

        startTransaction()
    }

    fun startTransaction() {
        listener = MyBBdeviceControllerListener()
        bbDeviceController = BBDeviceController.getInstance(context, listener)
        BBDeviceController.setDebugLogEnabled(true)
        toolbarTitle.value = "Initializing..."

        if (bbDeviceController!!.connectionMode == BBDeviceController.ConnectionMode.SERIAL) return
        bbDeviceController!!.startSerial()
    }

    private fun startEmv() {
        val data: Hashtable<String, Any> = Hashtable() //define empty hashmap

        data["emvOption"] = BBDeviceController.EmvOption.START
        data["orderID"] = "0123456789ABCDEF0123456789ABCDEF"
        data["randomNumber"] = "012345"

        // Terminal Time
        val terminalTime = SimpleDateFormat("yyMMddHHmmss").format(Calendar.getInstance().time)
        data["terminalTime"] = terminalTime

        // Check for Swipe, Insert and Tap card
        data.put("checkCardMode", BBDeviceController.CheckCardMode.SWIPE_OR_INSERT_OR_TAP)
        startAnimation.value = true
        bbDeviceController!!.startEmv(data)
    }

    private fun promptForAmount() {
        val input: Hashtable<String, Any> = Hashtable() //define empty hashmap

        // Amount
        var currencyCharacters = listOf(BBDeviceController.CurrencyCharacter.P, BBDeviceController.CurrencyCharacter.H, BBDeviceController.CurrencyCharacter.P)

        input.put("amount", "20000")
        input.put("transactionType", BBDeviceController.TransactionType.GOODS)
        input.put("currencyCode", "604")
        bbDeviceController!!.setAmount(input)
    }

    fun stopConnection() {
        bbDeviceController!!.releaseBBDeviceController()
    }

    inner private class MyBBdeviceControllerListener : BBDeviceController.BBDeviceControllerListener {
        override fun onReturnUpdateAIDResult(p0: Hashtable<String, BBDeviceController.TerminalSettingStatus>?) {

        }

        override fun onReturnAccountSelectionResult(p0: BBDeviceController.AccountSelectionResult?, p1: Int) {

        }

        override fun onReturnEmvCardNumber(p0: Boolean, p1: String?) {

        }

        override fun onDeviceDisplayingPrompt() {

        }

        override fun onRequestSelectApplication(p0: ArrayList<String>?) {

        }

        override fun onRequestDisplayText(displayText: BBDeviceController.DisplayText?) {
            toolbarTitle.value = displayText.toString()

        }

        override fun onReturnPrintResult(p0: BBDeviceController.PrintResult?) {

        }

        override fun onReturnDisableAccountSelectionResult(p0: Boolean) {

        }

        override fun onReturnAmount(p0: Hashtable<String, String>?) {

        }

        override fun onBTConnected(p0: BluetoothDevice?) {

        }

        override fun onReturnApduResult(p0: Boolean, p1: Hashtable<String, Any>?) {

        }

        override fun onReturnCAPKDetail(p0: CAPK?) {

        }

        override fun onReturnReversalData(p0: String?) {

        }

        override fun onReturnReadAIDResult(p0: Hashtable<String, Any>?) {

        }

        override fun onRequestOnlineProcess(p0: String?) {

            bbDeviceController?.sendOnlineProcessResult("8A023030")

        }

        override fun onReturnNfcDataExchangeResult(p0: Boolean, p1: Hashtable<String, String>?) {

        }

        override fun onBTScanStopped() {

        }

        override fun onReturnPowerOnIccResult(p0: Boolean, p1: String?, p2: String?, p3: Int) {

        }

        override fun onReturnEmvReport(p0: String?) {

        }

        override fun onReturnPhoneNumber(p0: BBDeviceController.PhoneEntryResult?, p1: String?) {

        }

        override fun onReturnCAPKLocation(p0: String?) {

        }

        override fun onReturnEncryptPinResult(p0: Boolean, p1: Hashtable<String, String>?) {

        }

        override fun onAudioDeviceUnplugged() {

        }

        override fun onSessionError(p0: BBDeviceController.SessionError?, p1: String?) {

        }

        override fun onAudioAutoConfigCompleted(p0: Boolean, p1: String?) {

        }

        override fun onReturnEmvCardDataResult(p0: Boolean, p1: String?) {

        }

        override fun onReturnReadGprsSettingsResult(p0: Boolean, p1: Hashtable<String, Any>?) {

        }

        override fun onRequestClearDisplay() {

        }

        override fun onRequestTerminalTime() {

        }

        override fun onAudioAutoConfigError(p0: BBDeviceController.AudioAutoConfigError?) {

        }



        override fun onReturnReadTerminalSettingResult(p0: Hashtable<String, Any>?) {

        }

        override fun onReturnVasResult(p0: BBDeviceController.VASResult?, p1: Hashtable<String, Any>?) {

        }


        override fun onReturnControlLEDResult(p0: Boolean, p1: String?) {

        }

        override fun onReturnRemoveCAPKResult(p0: Boolean) {

        }

        override fun onBTReturnScanResults(p0: MutableList<BluetoothDevice>?) {

        }

        override fun onEnterStandbyMode() {

        }

        override fun onPrintDataEnd() {

        }

        override fun onReturnDisableInputAmountResult(p0: Boolean) {

        }

        override fun onSerialDisconnected() {

        }


        override fun onBTRequestPairing() {

        }

        override fun onDeviceHere(p0: Boolean) {

        }

        override fun onReturnEmvReportList(p0: Hashtable<String, String>?) {

        }

        override fun onReturnEnableInputAmountResult(p0: Boolean) {

        }

        override fun onAudioAutoConfigProgressUpdate(p0: Double) {

        }

        override fun onPowerButtonPressed() {

        }

        override fun onPrintDataCancelled() {

        }

        override fun onReturnNfcDetectCardResult(p0: BBDeviceController.NfcDetectCardResult?, p1: Hashtable<String, Any>?) {

        }

        override fun onPowerDown() {

        }

        override fun onReturnUpdateTerminalSettingResult(p0: BBDeviceController.TerminalSettingStatus?) {

        }

        override fun onAudioDevicePlugged() {

        }

        override fun onRequestKeypadResponse() {

        }

        override fun onNoAudioDeviceDetected() {

        }

        override fun onRequestSetAmount() {
            promptForAmount()
        }

        override fun onRequestDisplayLEDIndicator(p0: BBDeviceController.ContactlessStatus?) {

        }

        override fun onReturnFunctionKey(p0: BBDeviceController.FunctionKey?) {

        }

        override fun onReturnCAPKList(p0: MutableList<CAPK>?) {

        }

        override fun onRequestStartEmv() {

        }

        override fun onUsbDisconnected() {

        }

        override fun onReturnUpdateCAPKResult(p0: Boolean) {

        }

        override fun onRequestFinalConfirm() {
            bbDeviceController?.sendFinalConfirmResult(true)

        }

        override fun onReturnBarcode(p0: String?) {

        }

        override fun onReturnUpdateGprsSettingsResult(p0: Boolean, p1: Hashtable<String, BBDeviceController.TerminalSettingStatus>?) {

        }

        override fun onRequestPrintData(p0: Int, p1: Boolean) {

        }

        override fun onSerialConnected() {
            startEmv()
        }

        override fun onReturnBatchData(p0: String?) {

        }

        override fun onReturnEncryptDataResult(p0: Boolean, p1: Hashtable<String, String>?) {

        }

        override fun onBarcodeReaderConnected() {

        }

        override fun onRequestDisplayAsterisk(numOfAsterisk: Int) {
            var content = ""
//            if (BaseDeviceActivity.currentActivity is ActivityPinPad) {
//            } else {
//                content = "PIN" + ": "
//            }
//            for (i in 0 until numOfAsterisk) {
//                content += "*"
//            }
//            (BaseDeviceActivity.currentActivity as ActivityPinPad).setStars(content)
        }

        override fun onReturnDeviceInfo(p0: Hashtable<String, String>?) {

        }

        override fun onReturnCancelCheckCardResult(p0: Boolean) {

        }

        override fun onBatteryLow(p0: BBDeviceController.BatteryStatus?) {

        }

        override fun onBTScanTimeout() {

        }

        override fun onRequestProduceAudioTone(contactlessStatusTone: BBDeviceController.ContactlessStatusTone?) {
            // setStatus("Contactless Status Tone : $contactlessStatusTone")

//           val audio = currentActivity.getSystemService(Context.AUDIO_SERVICE) as AudioManager
//            val mode = audio.mode
//            val currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC)
//            audio.setStreamVolume(AudioManager.STREAM_MUSIC, audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * 3 / 4, 0)
//            audio.mode = AudioManager.MODE_NORMAL
////            if (contactlessStatusTone == BBDeviceController.ContactlessStatusTone.SUCCESS) {
////                MediaPlayer.create(baseContext, R.raw.beep_ace_success).start()
////            } else {
////                MediaPlayer.create(baseContext, R.raw.beep_ace_alert).start()
////            }
//
//            Handler().postDelayed({
//                // TODO Auto-generated method stub
//                audio.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0)
//                audio.mode = mode
//            }, 500)
        }

        override fun onBTDisconnected() {

        }

        override fun onWaitingReprintOrPrintNext() {

        }

        override fun onSessionInitialized() {

        }

        override fun onReturnEnableAccountSelectionResult(p0: Boolean) {

        }

        override fun onWaitingForCard(cardMode: BBDeviceController.CheckCardMode?) {
            when (cardMode) {
                BBDeviceController.CheckCardMode.INSERT -> {
                    toolbarTitle.value = "Please insert card"
                }
                BBDeviceController.CheckCardMode.SWIPE -> {
                    toolbarTitle.value = "Please swipe card"
                }
                BBDeviceController.CheckCardMode.SWIPE_OR_INSERT -> {
                    toolbarTitle.value = "Please swipe/insert card"
                }
                BBDeviceController.CheckCardMode.SWIPE_OR_TAP -> {
                    toolbarTitle.value = "Please swipe/tap card"
                }
                BBDeviceController.CheckCardMode.INSERT_OR_TAP -> {
                    toolbarTitle.value = "Please inert/tap card"
                }
                BBDeviceController.CheckCardMode.SWIPE_OR_INSERT_OR_TAP -> {
                    toolbarTitle.value = "Please swipe/insert/tap card"
                }
            }

        }

        override fun onReturnCheckCardResult(p0: BBDeviceController.CheckCardResult?, p1: Hashtable<String, String>?) {

        }

        override fun onRequestPinEntry(pinEntrySource: BBDeviceController.PinEntrySource?) {

            if (pinEntrySource == BBDeviceController.PinEntrySource.SMARTPOS) {
                  pinButtonLayout = Hashtable<String,Rect>()


                pinButtonLayout["key1"] = Rect(50, 400, 255, 550)
                pinButtonLayout["key2"] = Rect(265, 400, 470, 550)
                pinButtonLayout["key3"] = Rect(480, 400, 685, 550)

                pinButtonLayout["key4"] = Rect(50, 560, 255, 710)
                pinButtonLayout["key5"] = Rect(265, 560, 470, 710)
                pinButtonLayout["key6"] = Rect(480, 560, 685, 710)

                pinButtonLayout["key7"] = Rect(50, 720, 255, 870)
                pinButtonLayout["key8"] = Rect(265, 720, 470, 870)
                pinButtonLayout["key9"] = Rect(480, 720, 685, 870)

                pinButtonLayout["cancel"] = Rect(50, 880, 255, 1030)
                pinButtonLayout["key0"] = Rect(265, 880, 470, 1030)
                pinButtonLayout["clear"] = Rect(480, 880, 685, 1030)

                pinButtonLayout["enter"] = Rect(50, 1040, 685, 1190)

                bbDeviceController!!.setPinPadButtons(pinButtonLayout)
            }
        }

        override fun onReturnSetPinPadButtonsResult(p0: Boolean) {

            onDestroy()

            view!!.findNavController().navigate(R.id.pinPadFragment)
//            finish()
//            val intent = Intent(currentActivity, ActivityPinPad::class.java)
//            intent.putExtra("amount", BaseDeviceActivity.currentActivity.intent.getStringExtra("amount"))
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
//            startActivity(intent)

        }

        override fun onReturnPinEntryResult(pinEntryResult: BBDeviceController.PinEntryResult?, data: Hashtable<String, String>) {
//            switchBackFromWisePOSPlusPin()

            if (pinEntryResult == BBDeviceController.PinEntryResult.ENTERED) run {
                if (data.containsKey("epb")) {
                    data.get("epb")
                }
                if (data.containsKey("ksn")) {
                    data.get("ksn")
                }
                if (data.containsKey("randomNumber")) {
                    data.get("randomNumber")
                }
                if (data.containsKey("encWorkingKey")) {
                    data.get("encWorkingKey")
                }
            } else if (pinEntryResult == BBDeviceController.PinEntryResult.BYPASS) {

            } else if (pinEntryResult == BBDeviceController.PinEntryResult.CANCEL) {

            } else if (pinEntryResult == BBDeviceController.PinEntryResult.TIMEOUT) {

            }
        }

        override fun onReturnTransactionResult(result: BBDeviceController.TransactionResult?) {

            if (result == BBDeviceController.TransactionResult.APPROVED){
                onResult.value = true
            }
            stopConnection()

        }
        override fun onDeviceReset() {

        }

        override fun onReturnReadWiFiSettingsResult(p0: Boolean, p1: Hashtable<String, Any>?) {

        }

        override fun onReturnDisplayPromptResult(p0: BBDeviceController.DisplayPromptResult?) {

        }

        override fun onBarcodeReaderDisconnected() {

        }

        override fun onUsbConnected() {

        }

        override fun onReturnUpdateWiFiSettingsResult(p0: Boolean, p1: Hashtable<String, BBDeviceController.TerminalSettingStatus>?) {

        }

        override fun onError(p0: BBDeviceController.Error?, p1: String?) {

        }

        override fun onReturnAmountConfirmResult(p0: Boolean) {

        }

        override fun onReturnInjectSessionKeyResult(p0: Boolean, p1: Hashtable<String, String>?) {

        }

        override fun onReturnPowerOffIccResult(p0: Boolean) {

        }
    }
}