package com.a.romsmarthomeapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "devices")
data class Device(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 1,
    @ColumnInfo(name = "name")  val deviceName: String?,
    @ColumnInfo(name = "intensity")  val intensity: String?,
    @ColumnInfo(name = "mode")  val mode: String?,
    @ColumnInfo(name = "position")  val position: Int?,
    @ColumnInfo(name = "productType")  val productType: String?,
    @ColumnInfo(name = "temperature")  val temperature: Int?,

){

    suspend fun deleteDevice(device: Device)  { deleteDevice (device) }

}