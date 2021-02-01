package com.spark.domain.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.spark.BuildConfig
import com.spark.data.api.RestApi
import com.spark.data.repositories.ReligionsRepositoryImpl
import com.spark.data.utils.EndPoints
import com.spark.domain.repositories.ReligionsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideReligionRepository(restApi:RestApi ): ReligionsRepository {
        return ReligionsRepositoryImpl(restApi)
    }

}