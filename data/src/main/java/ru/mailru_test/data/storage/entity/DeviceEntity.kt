package ru.mailru_test.data.storage.entity

import androidx.room.Entity

@Entity(primaryKeys = ["position", "appId"])
data class DeviceEntity(
    val appId: String,
    val position: Int,
    val device: String? = null,
    var checked: Boolean,
    var saved: Boolean,
    var error: Boolean = false
) 