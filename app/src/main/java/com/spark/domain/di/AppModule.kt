package com.spark.domain.di

import com.spark.data.api.RestApi
import com.spark.data.repositories.EthnicityRepositoryImpl
import com.spark.data.repositories.MaritalListRepositoryImpl
import com.spark.data.repositories.ProfileRepositoryImpl
import com.spark.data.repositories.ReligionsRepositoryImpl
import com.spark.domain.repositories.EthnicityRepository
import com.spark.domain.repositories.MaritalListRepository
import com.spark.domain.repositories.ProfileRepository
import com.spark.domain.repositories.ReligionsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideReligionRepository(restApi: RestApi): ReligionsRepository {
        return ReligionsRepositoryImpl(restApi)
    }

    @Provides
    @Singleton
    fun provideEthnicRepository(restApi: RestApi): EthnicityRepository {
        return EthnicityRepositoryImpl(restApi)
    }

    @Provides
    @Singleton
    fun provideMaritalRepository(restApi: RestApi): MaritalListRepository {
        return MaritalListRepositoryImpl(restApi)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(restApi: RestApi): ProfileRepository {
        return ProfileRepositoryImpl(restApi)
    }

}