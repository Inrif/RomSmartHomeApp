package com.a.romsmarthomeapp.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import retrofit2.Converter


@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 1,
//    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 1,
    @TypeConverters(Converter::class)
    @ColumnInfo(name = "address") val address: Address?,
//    @ColumnInfo(name = "birthDate") var birthDate: Long?,
    @ColumnInfo(name = "birthDate") var birthDate: String?,
    @ColumnInfo(name = "firstName") var firstName: String?,
    @ColumnInfo(name = "lastName") var lastName: String?,

    )