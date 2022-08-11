package com.a.romsmarthomeapp.api

import com.a.romsmarthomeapp.model.Device
import com.a.romsmarthomeapp.model.DeviceList
import com.a.romsmarthomeapp.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by HOUNSA ROMUALD on 02/08/22.
 * Copyright (c) 2022 com.a.romsmarthomeapp. All rights reserved.
 */
interface DeviceApiService {

    @GET("data.json")
    suspend fun getAllDevices() : Response<DeviceList>

    @GET("device/{id}")
    suspend fun getDevice(@Path("id") id: Int): Response<Device>


}