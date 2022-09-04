package com.a.romsmarthomeapp.api

import android.content.ContentValues.TAG
import android.util.Log
import com.a.romsmarthomeapp.utils.Resource
import retrofit2.Response
import timber.log.Timber

/**
 * Created by HOUNSA ROMUALD on 04/08/22.
 * Copyright (c) 2022 com.a.romsmarthomeapp. All rights reserved.
 */
abstract class BaseApiDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()

                Log.d(TAG, "getResult:" +body)
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Timber.d(message)
        return Resource.error("Network call has failed for a following reason: $message")
    }



}