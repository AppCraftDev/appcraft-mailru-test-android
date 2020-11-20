package ru.mailru_test.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.mailru_test.data.storage.converter.CommonConverters
import ru.mailru_test.data.storage.dao.UserDao
import ru.mailru_test.data.storage.entity.DeviceEntity

@Database(
    entities = [
        DeviceEntity::class
    ],
    version = 48,
    exportSchema = false
)
@TypeConverters(CommonConverters::class)
abstract class SystemDb : RoomDatabase() {

    abstract fun getUserDao(): UserDao

}