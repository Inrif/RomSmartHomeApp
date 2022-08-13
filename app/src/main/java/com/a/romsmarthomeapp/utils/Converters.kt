package com.a.romsmarthomeapp.utils

import androidx.room.TypeConverter
import com.a.romsmarthomeapp.model.Address
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by HOUNSA ROMUALD on 09/08/22.
 */


class Converters {

    @TypeConverter
    fun fromString(value: String): Address {
        val address = object : TypeToken<Address>() {}.type
        return Gson().fromJson(value, address)
    }

    @TypeConverter
    fun fromListLisr(value: Address): String {
        val gson = Gson()
        return gson.toJson(value)
    }
}
