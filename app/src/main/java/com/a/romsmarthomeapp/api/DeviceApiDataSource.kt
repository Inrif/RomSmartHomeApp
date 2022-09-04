package com.a.romsmarthomeapp.api

import javax.inject.Inject

/**
 * Created by HOUNSA ROMUALD on 04/08/22.
 * Copyright (c) 2022 com.a.romsmarthomeapp. All rights reserved.
 */


class DeviceApiDataSource @Inject constructor(
    private val deviceApiService: DeviceApiService
): BaseApiDataSource() {

    suspend fun getDevices() = getResult { deviceApiService.getAllDevices() }
    suspend fun getDevice(id: Int) = getResult { deviceApiService.getDevice(id) }
//    suspend fun getAlltypeUser(id: Long) = getResult { deviceApiService.getAlltypeUser(id) }
//    suspend fun getAllUsers() = getResult { deviceApiService.getAllUsers() }

}