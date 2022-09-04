package com.a.romsmarthomeapp.ui.homePage


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.a.romsmarthomeapp.model.Device
import com.a.romsmarthomeapp.repository.DeviceRepository
import com.a.romsmarthomeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * Created by HOUNSA ROMUALD on 05/08/22.
 * Copyright (c) 2022 com.a.romsmarthomeapp. All rights reserved.
 */

@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val repository: DeviceRepository
) : ViewModel() {

    val devices = repository.getDevices()



}