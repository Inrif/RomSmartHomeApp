package com.a.romsmarthomeapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import retrofit2.Converter


@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 1,
    @TypeConverters(Converter::class)
    @ColumnInfo(name = "address")  val address: Address?,
    @ColumnInfo(name = "birthDate")  val birthDate: Long?,
    @ColumnInfo(name = "firstName")  val firstName: String?,
    @ColumnInfo(name = "lastName")  val lastName: String?,

    )