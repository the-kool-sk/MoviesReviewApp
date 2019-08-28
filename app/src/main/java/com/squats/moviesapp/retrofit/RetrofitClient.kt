package com.parshwahotel.parshwahotelapp.repository.retrofit

import android.app.Application
import com.parshwahotel.parshwahotelapp.extentionfunctions.isConnectedToInternet
import com.parshwahotel.parshwahotelapp.utility.NetworkConstants
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    var retrofit: EndPoints
    //lateinit val application:Application

    init {
        val interceptor = HttpLoggingInterceptor()
        val cacheSize = (5 * 1024 * 1024).toLong()
      //  val movieCache = Cache(application.cacheDir, cacheSize)
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor)
            //.cache(movieCache).
             /* addInterceptor { chain ->
                var request = chain.request()
                request = if (application.isConnectedToInternet())
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 10).build()
                else
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                chain.proceed(request)
            }*/
            .retryOnConnectionFailure(false)
            .build()
        retrofit = Retrofit.Builder().baseUrl(NetworkConstants.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build().create(EndPoints::class.java)
    }

    fun getRetrofitClient(mapplication: Application): EndPoints {
        //this.application=mapplication
        return retrofit
    }


}