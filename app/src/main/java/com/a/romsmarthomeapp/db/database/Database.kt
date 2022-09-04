package com.a.romsmarthomeapp.db.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.a.romsmarthomeapp.db.dao.DeviceDao
import com.a.romsmarthomeapp.db.dao.UserDao
import com.a.romsmarthomeapp.model.Device
import com.a.romsmarthomeapp.model.User
import com.a.romsmarthomeapp.utils.Converters

/**
 * Created by HOUNSA ROMUALD on 02/08/22.
 * Copyright (c) 2022 com.a.romsmarthomeapp. All rights reserved.
 */


@androidx.room.Database(entities = [Device::class, User::class], version = 1, exportSchema = false)

@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {

    abstract fun deviceDao(): DeviceDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var instance: Database? = null

        fun getDatabase(context: Context): Database =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, Database::class.java, "devices")
                .fallbackToDestructiveMigration()
                .build()
    }

}

