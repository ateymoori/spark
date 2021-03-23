package com.spark.domain.di

import com.spark.data.api.RestApi
import com.spark.data.repositories.*
import com.spark.domain.repositories.*
import com.spark.presentation.utils.components.base.AssetsUtils
import com.spark.presentation.utils.components.base.SharedPrefUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
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
    fun provideProfileRepository(restApi: RestApi , shartedPrefUtils : SharedPrefUtils): ProfileRepository {
        return ProfileRepositoryImpl(restApi,shartedPrefUtils )
    }

    @Provides
    @Singleton
    fun provideLocationRepository(assetsUtils: AssetsUtils ): LocationRepository {
        return LocationRepositoryImpl(assetsUtil = assetsUtils)
    }

}