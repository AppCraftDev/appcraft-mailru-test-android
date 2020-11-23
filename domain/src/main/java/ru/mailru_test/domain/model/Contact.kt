package ru.mailru_test.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    val id: Long,
    var label: String,
    val avatar: String?,
    val avatarThumbnail: String?,
    var phoneNumbers: MutableList<String> = ArrayList(),
    val type: String?
) : Parcelable