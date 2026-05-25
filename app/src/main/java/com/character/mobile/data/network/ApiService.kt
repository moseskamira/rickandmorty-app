package com.character.mobile.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiService {

    private var interceptor = HttpLoggingInterceptor()
    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)).build()

    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Apis.BASEURL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun getRetrofitServiceApi(): ApiClient {
        return getRetrofitInstance().create(ApiClient::class.java)
    }
}