package com.a.romsmarthomeapp.repository

import com.a.romsmarthomeapp.api.DeviceApiDataSource
import com.a.romsmarthomeapp.db.dao.DeviceDao
import com.a.romsmarthomeapp.model.Device
import com.a.romsmarthomeapp.model.User
import com.a.romsmarthomeapp.utils.Resource
import com.a.romsmarthomeapp.utils.performGetOperation
import javax.inject.Inject

/**
 * Created by HOUNSA ROMUALD on 04/08/22.
 * Copyright (c) 2022 com.a.romsmarthomeapp. All rights reserved.
 */



class DeviceRepository @Inject constructor(
    private val apiDataSource: DeviceApiDataSource,
    private val dbDataSource: DeviceDao,
) {

//    fun getDevice(id: Int) = performGetOperation(
//        databaseQuery = { dbDataSource.getDevice(id) },
//        networkCall = { apiDataSource.getDevice(id) }
//    ) { dbDataSource.insert(it) }
//
//    fun getDevices() = performGetOperation(
//        databaseQuery = { dbDataSource.getAllDevices()},
//        networkCall = { apiDataSource.getDevices() }
//    ) { dbDataSource.insertAll(it.devices) }


    fun getDevice(id: Int) = performGetOperation(
        databaseQuery = { dbDataSource.getDevice(id) },
        networkCall = { apiDataSource.getDevice(id) },
        saveCallResult = { dbDataSource.insert(it) }
    )

    fun getDevices() = performGetOperation(
        databaseQuery = { dbDataSource.getAllDevices()},
        networkCall = { apiDataSource.getDevices() },
        saveCallResult = { dbDataSource.insertAll(it.devices) }
    )

    fun updateDevice(id: Int) = performGetOperation(
        databaseQuery = { dbDataSource.getDevice(id)},
        networkCall = { apiDataSource.getDevice(id) },
        saveCallResult = { dbDataSource.updateDevice(it) }
    )
//
//   fun updateDevice(device: Device)

//    fun updateUser() = performGetOperation(
//        databaseQuery = { dbDataSource.getUser() },
//        networkCall = { apiDataSource.getDevices() },
//        saveCallResult = { dbDataSource.updateUser(it.user) }
//    )

    fun deleteDevice(id: Int)  { dbDataSource.deleteDevice(id)}





}