package com.a.romsmarthomeapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.a.romsmarthomeapp.model.Device
import com.a.romsmarthomeapp.model.User
import retrofit2.http.DELETE

/**
 * Created by HOUNSA ROMUALD on 02/08/22.
 * Copyright (c) 2022 com.a.romsmarthomeapp. All rights reserved.
 */
@Dao
interface UserDao {


    @Query("SELECT * FROM users")
    fun getAllUsers() : LiveData<List<User>>

    @Query("SELECT * FROM users WHERE id = 1")
    fun getUser(): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun  updateUser(user: User)


}