package com.a.romsmarthomeapp.di

import android.content.Context
import com.a.romsmarthomeapp.api.DeviceApiDataSource
import com.a.romsmarthomeapp.api.DeviceApiService
import com.a.romsmarthomeapp.db.dao.DeviceDao
import com.a.romsmarthomeapp.db.dao.UserDao
import com.a.romsmarthomeapp.db.database.Database
import com.a.romsmarthomeapp.repository.DeviceRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by HOUNSA ROMUALD on 02/08/22.
 * Copyright (c) 2022 com.a.romsmarthomeapp. All rights reserved.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("http://storage42.com/modulotest/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun getGson(): Gson = GsonBuilder().create()

    @Provides
    fun getDeviceService(retrofit: Retrofit): DeviceApiService = retrofit.create(DeviceApiService::class.java)

    @Singleton
    @Provides
    fun getDeviceDataSource(DeviceApiService: DeviceApiService) = DeviceApiDataSource(DeviceApiService)

    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext appContext: Context) = Database.getDatabase(appContext)

    @Singleton
    @Provides
    fun getDeviceDao(db: Database) = db.deviceDao()

    @Singleton
    @Provides
    fun getUserDao(db: Database) = db.userDao()

    @Singleton
    @Provides
    fun getRepository(apiDataSource: DeviceApiDataSource,
                      dbDataSource: DeviceDao, userDao: UserDao
    ) =
      DeviceRepository(apiDataSource, dbDataSource)
}