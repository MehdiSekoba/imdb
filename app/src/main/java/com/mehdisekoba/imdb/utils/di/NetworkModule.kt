package com.mehdisekoba.imdb.utils.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mehdisekoba.imdb.BuildConfig
import com.mehdisekoba.imdb.data.network.ApiServices
import com.mehdisekoba.imdb.utils.API_KEY
import com.mehdisekoba.imdb.utils.AUTHORIZATION
import com.mehdisekoba.imdb.utils.BASE_URL
import com.mehdisekoba.imdb.utils.BEARER
import com.mehdisekoba.imdb.utils.CONNECTION_TIME
import com.mehdisekoba.imdb.utils.NAMED_PING
import com.mehdisekoba.imdb.utils.PING_INTERVAL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        gson: Gson,
        client: OkHttpClient,
    ): ApiServices =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServices::class.java)

    @Provides
    @Singleton
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideClient(
        timeout: Long,
        @Named(NAMED_PING) ping: Long,
        interceptor: HttpLoggingInterceptor,
    ) = OkHttpClient.Builder()
        .addInterceptor { chain ->
            chain.proceed(
                chain.request().newBuilder().also {
                    it.addHeader(AUTHORIZATION, "$BEARER $API_KEY")
                }.build(),
            )
        }.also { client ->
            client.addInterceptor(interceptor)
        }
        .writeTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .pingInterval(ping, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideInterceptor() =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    @Singleton
    fun provideTimeout() = CONNECTION_TIME

    @Provides
    @Singleton
    @Named(NAMED_PING)
    fun providePingInterval() = PING_INTERVAL
}
