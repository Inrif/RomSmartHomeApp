package com.a.romsmarthomeapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.a.romsmarthomeapp.model.Device
import com.a.romsmarthomeapp.model.DeviceList
import com.a.romsmarthomeapp.utils.Resource
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

/**
 * Created by HOUNSA ROMUALD on 02/08/22.
 * Copyright (c) 2022 com.a.romsmarthomeapp. All rights reserved.
 */

@Dao
interface DeviceDao {

    @Query("SELECT * FROM devices")
    fun getAllDevices() : LiveData<List<Device>>


    @Query("SELECT * FROM devices WHERE id = :id")
    fun getDevice(id: Int): LiveData<Device>

    @Query("DELETE FROM devices  WHERE id = :id")
    fun deleteDevice(id: Int?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(devices: List<Device>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(device: Device)



}