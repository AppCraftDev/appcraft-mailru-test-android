package ru.mailru_test.data.repository

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.ArrayMap
import ru.mailru_test.domain.model.Contact
import ru.mailru_test.domain.repository.ContactsRepository

class ContactsRepositoryImpl(
    private val context: Context
) : ContactsRepository {

    override suspend fun getContacts(): List<Contact> {
        val contacts = arrayListOf<Contact>()
        val projection = arrayOf(
            ContactsContract.Data.CONTACT_ID,
            ContactsContract.Data.DISPLAY_NAME_PRIMARY,
            ContactsContract.Data.PHOTO_URI,
            ContactsContract.Data.PHOTO_THUMBNAIL_URI,
            ContactsContract.Data.DATA1,
            ContactsContract.Data.MIMETYPE
        )
        val cursor = context.contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            projection,
            null,
            null,
            ContactsContract.Data.CONTACT_ID
        )
        if (cursor != null) {
            val mapped = getDataMap(cursor)
            contacts.addAll(mapped.values)
        }
        return contacts
    }

    private fun getDataMap(cursor: Cursor): ArrayMap<Long, Contact> {
        val contacts = ArrayMap<Long, Contact>()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID))
            val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME_PRIMARY))
            val avatar = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.PHOTO_URI))
            val avatarThumbnail = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.PHOTO_THUMBNAIL_URI))
            val type = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE))

            val contact: Contact = contacts[id] ?: Contact(
                id = id,
                label = name,
                avatar = avatar,
                avatarThumbnail = avatarThumbnail,
                type = type
            ).apply {
                contacts[id] = this
            }

            if (type == ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE) {
                mapPhoneNumber(contact, cursor)
            }
        }
        cursor.close()
        return contacts
    }

    private fun mapPhoneNumber(contact: Contact, cursor: Cursor) {
        val phone = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DATA1))
        if (!phone.isNullOrEmpty()) {
            val phoneFormatted = phone.replace("\\s+".toRegex(), "")
            contact.phoneNumbers = contact.phoneNumbers.orEmpty().toMutableList().apply {
                add(phoneFormatted)
            }
        }
    }
}