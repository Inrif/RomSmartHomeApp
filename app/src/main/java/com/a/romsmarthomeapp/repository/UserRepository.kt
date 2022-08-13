package com.a.romsmarthomeapp.repository

import com.a.romsmarthomeapp.api.DeviceApiDataSource
import com.a.romsmarthomeapp.db.dao.DeviceDao
import com.a.romsmarthomeapp.db.dao.UserDao
import com.a.romsmarthomeapp.model.User
import com.a.romsmarthomeapp.utils.performGetOperation
import javax.inject.Inject

/**
 * Created by HOUNSA ROMUALD on 09/08/22.
 * Copyright (c) 2022 com.a.romsmarthomeapp. All rights reserved.
 */
class UserRepository @Inject constructor(
    private val apiDataSource: DeviceApiDataSource,
    private val dbDataSource: UserDao
) {

    fun getUser() = performGetOperation(
        databaseQuery = { dbDataSource.getUser() },
        networkCall = { apiDataSource.getDevices() },
        saveCallResult = { dbDataSource.insertUser(it.user) }
    )



}