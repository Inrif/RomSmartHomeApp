package com.a.romsmarthomeapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by HOUNSA ROMUALD on 05/08/22.
 * Copyright (c) 2022 com.a.romsmarthomeapp. All rights reserved.
 */

@HiltAndroidApp
class DeviceApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}