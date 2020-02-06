package com.xpayworld.payment.network

import com.xpayworld.payment.BuildConfig
import io.reactivex.schedulers.Schedulers
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {


    fun getRetrofit(): Retrofit {
        val certPinner: CertificatePinner = CertificatePinner.Builder()
                .add(ApiConstants.API_HOST,
                        "sha256/4hw5tz+scE+TW+mlai5YipDfFWn1dqvfLG+nU7tq1V8=")
                .build()


        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .certificatePinner(certPinner)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(ApiConstants.API_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())).client(okHttpClient)
            .build()
    }
}