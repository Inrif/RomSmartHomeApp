package com.a.romsmarthomeapp.ui.myAccount

import androidx.lifecycle.*
import com.a.romsmarthomeapp.model.Device
import com.a.romsmarthomeapp.model.User
import com.a.romsmarthomeapp.repository.DeviceRepository
import com.a.romsmarthomeapp.repository.UserRepository
import com.a.romsmarthomeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by HOUNSA ROMUALD on 08/08/22.
 * Copyright (c) 2022 com.a.romsmarthomeapp. All rights reserved.
 */

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,

) : ViewModel() {

    val user = repository.getUser()
    val userUpdate = repository.updateUser()
}