package com.xpayworld.payment.ui.transaction.processTransaction

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.bbpos.bbdevice.BBDeviceController
import  com.bbpos.bbdevice.BBDeviceController.CheckCardMode
import com.bbpos.bbdevice.BBDeviceController.BBDeviceControllerListener
import com.bbpos.bbdevice.CAPK
import com.bbpos.bbdevice.ota.BBDeviceOTAController
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.transaction.DashboardActivity
import kotlinx.android.synthetic.main.fragment_enter_amount.*
import java.text.SimpleDateFormat
import java.util.*



class BBPosViewModel(activity: Activity) : ViewModel() {


    internal var bbDeviceController: BBDeviceController? = null
    internal lateinit var otaController: BBDeviceOTAController
    var listener: MyBBdeviceControllerListener? = null

    val cancelClickListener = View.OnClickListener { onClickCancel(it) }

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val toolbarTitle: MutableLiveData<String> = MutableLiveData()
    val startAnimation: MutableLiveData<Boolean> = MutableLiveData()
    val showEnterPin : MutableLiveData<Boolean> = MutableLiveData()
    val setPinLayout : MutableLiveData<Hashtable<String, Rect>> =  MutableLiveData()

    var pinButtonLayout: Hashtable<String, Rect> = Hashtable()

    var currentActivity = activity

    init {


    }

    fun  startTransaction() {
        listener = MyBBdeviceControllerListener()
        bbDeviceController = BBDeviceController.getInstance(currentActivity, listener)
        BBDeviceController.setDebugLogEnabled(true)
        toolbarTitle.value = "Initializing..."
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
        data.put("checkCardMode", CheckCardMode.SWIPE_OR_INSERT_OR_TAP)

        bbDeviceController!!.startEmv(data)
        startAnimation.value = true
    }

    private fun promptForAmount() {
        val input: Hashtable<String, Any> = Hashtable() //define empty hashmap

        // Amount
        var currencyCharacters = listOf(BBDeviceController.CurrencyCharacter.P, BBDeviceController.CurrencyCharacter.H, BBDeviceController.CurrencyCharacter.P)

        input.put("amount", "20000")
        input.put("transactionType", BBDeviceController.TransactionType.GOODS)
        input.put("currencyCode", "604")
        // input.put("currencyCharacters", currencyCharacters)
        bbDeviceController!!.setAmount(input)
    }

    private fun onClickCancel(v: View) {
        stopConnection()
        v.findNavController().popBackStack()
    }

    private fun stopConnection() {
        bbDeviceController!!.releaseBBDeviceController()
    }


    internal fun switchBackFromWisePOSPlusPin() {
        if (currentActivity is ActivityPinPad) {
            //isSwitchingActivity = true
            currentActivity.finish()
            val intent = Intent(currentActivity, DashboardActivity::class.java)
            currentActivity.startActivity(intent)
            return
        }
    }
    inner class MyBBdeviceControllerListener : BBDeviceControllerListener {
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

        override fun onReturnTransactionResult(result: BBDeviceController.TransactionResult?) {
            toolbarTitle.value = result.toString()
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
            toolbarTitle.value = "Disconnected"
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
            println("start Serial connection")

        }

        override fun onReturnBatchData(p0: String?) {

        }

        override fun onReturnEncryptDataResult(p0: Boolean, p1: Hashtable<String, String>?) {

        }

        override fun onBarcodeReaderConnected() {

        }

        override fun onRequestDisplayAsterisk(p0: Int) {
//            var content = ""
//            if (activity is ActivityPinPad) {
//            } else {
//                content = getString(R.string.pin) + ": "
//            }
//            for (i in 0 until numOfAsterisk) {
//                content += "*"
//            }
//            statusEditText.setText(content)
//            currentActivity.setStars(content)

        }

        override fun onReturnDeviceInfo(p0: Hashtable<String, String>?) {

        }

        override fun onReturnCancelCheckCardResult(p0: Boolean) {

        }

        override fun onBatteryLow(p0: BBDeviceController.BatteryStatus?) {

        }

        override fun onBTScanTimeout() {

        }

        override fun onRequestProduceAudioTone(p0: BBDeviceController.ContactlessStatusTone?) {

        }

        override fun onBTDisconnected() {

        }

        override fun onWaitingReprintOrPrintNext() {

        }

        override fun onSessionInitialized() {

        }

        override fun onReturnEnableAccountSelectionResult(p0: Boolean) {

        }

        override fun onWaitingForCard(cardMode: CheckCardMode?) {

            when (cardMode) {
                CheckCardMode.INSERT -> {
                    toolbarTitle.value = "Please insert card"
                }
                CheckCardMode.SWIPE -> {
                    toolbarTitle.value = "Please swipe card"
                }
                CheckCardMode.SWIPE_OR_INSERT -> {
                    toolbarTitle.value = "Please swipe/insert card"
                }
                CheckCardMode.SWIPE_OR_TAP -> {
                    toolbarTitle.value = "Please swipe/tap card"
                }
                CheckCardMode.INSERT_OR_TAP -> {
                    toolbarTitle.value = "Please inert/tap card"
                }
                CheckCardMode.SWIPE_OR_INSERT_OR_TAP -> {
                    toolbarTitle.value = "Please swipe/insert/tap card"
                }
            }
        }

        override fun onReturnCheckCardResult(p0: BBDeviceController.CheckCardResult?, p1: Hashtable<String, String>?) {

        }

        override fun onRequestPinEntry(pinEntrySource: BBDeviceController.PinEntrySource?) {

            if (pinEntrySource == BBDeviceController.PinEntrySource.SMARTPOS) {
                pinButtonLayout = Hashtable()
//                pinButtonLayout["key1"] = Rect(50, 300, 255, 450)
//                pinButtonLayout["key2"] = Rect(265, 300, 470, 450)
//                pinButtonLayout["key3"] = Rect(480, 300, 685, 450)
//
//                pinButtonLayout["key4"] = Rect(50, 460, 255, 610)
//                pinButtonLayout["key5"] = Rect(265, 460, 470, 610)
//                pinButtonLayout["key6"] = Rect(480, 460, 685, 610)
//
//                pinButtonLayout["key7"] = Rect(50, 620, 255, 770)
//                pinButtonLayout["key8"] = Rect(265, 620, 470, 770)
//                pinButtonLayout["key9"] = Rect(480, 620, 685, 770)
//
//                pinButtonLayout["cancel"] = Rect(50, 780, 255, 930)
//                pinButtonLayout["key0"] = Rect(265, 780, 470, 930)
//                pinButtonLayout["clear"] = Rect(480, 780, 685, 930)
//
//                pinButtonLayout["enter"] = Rect(50, 940, 685, 1090)


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

               // setPinLayout.value = pinButtonLayout
               bbDeviceController!!.setPinPadButtons(pinButtonLayout)

            }
        }
        var wisePOSPlusPinPadView  : View? = null

        override fun onReturnSetPinPadButtonsResult(p0: Boolean) {

//           wisePOSPlusPinPadView = WisePOSPlusPinPadView(currentActivity, pinButtonLayout)

           // showEnterPin.value = p0
        //       setPinLayout.value = pinButtonLayout

            val intent = Intent(currentActivity, ActivityPinPad::class.java)
            intent.putExtra("dd", pinButtonLayout)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            currentActivity.startActivity(intent)


        }

        override fun onReturnPinEntryResult(pinEntryResult: BBDeviceController.PinEntryResult, data: Hashtable<String, String>) {


            (currentActivity as PipadDelegate).getActivity().finish()
            val i = Intent(currentActivity,DashboardActivity::class.java)
            currentActivity.startActivity(i)

         //  (currentActivity as PipadDelegate)?.getActivity().finish()
         //   showEnterPin.value = true

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

        override fun onError(p0: BBDeviceController.Error?, error: String?) {
            toolbarTitle.value = error
        }

        override fun onReturnAmountConfirmResult(p0: Boolean) {

        }

        override fun onReturnInjectSessionKeyResult(p0: Boolean, p1: Hashtable<String, String>?) {

        }

        override fun onReturnPowerOffIccResult(p0: Boolean) {

        }

    }
}