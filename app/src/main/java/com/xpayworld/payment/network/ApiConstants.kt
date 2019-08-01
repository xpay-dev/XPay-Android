package com.xpayworld.payment.network

//GLOBAL HEADERS

class  ApiConstants {


    companion object {
        private const val Rest = "Rest/"
        const val Content = "Content-Type: text/json"
        const val Charset = "Accept-Charset: utf-8"
        const val ActivateApp = Rest + "ActivateApp"
        const val API_HOST = "https://ph.veritaspay.com/paymayav2/webservice/POSWebServiceJSON.svc/"

        const val EmailReceipt = Rest + "EmailReceipt"
        const val ForgotMobilePin = Rest + "ForgotMobilePin"
        const val Login = Rest + "Login"
        const val Registration = Rest + "Registration"
        const val CreateTicket = Rest + "CreateTicket"
    }

}