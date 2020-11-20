package ru.mailru_test.data.storage.dao

import androidx.room.Dao

@Dao
interface UserDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertDevices(data: List<DevicesEntity>)
//
//    @Query("SELECT * FROM DevicesEntity")
//    fun getDevices(): Flow<List<DevicesEntity>>
//
//    @Query("DELETE FROM DevicesEntity")
//    suspend fun deleteDevices()
//
//    @Transaction
//    suspend fun deleteAndInsertDevices(data: List<DevicesEntity>) {
//        deleteDevices()
//        insertDevices(data)
//    }
}