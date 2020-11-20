package ru.mailru_test.data.mapper

object NotificationMapper {
//    val notificationModelMapper: Mapper<RawNotificationParams, NotificationModel> = {
//        val data = it.data
//        val title = it.title ?: data["title"] ?: empty()
//        val message = it.body ?: data["body"] ?: empty()
//
//        val gson = Gson()
//        val type = NotificationType.getByCode(data["notificationType"]?.toIntOrNull())
//        val meta = data["metadata"]
//        val id = meta?.let {
//            gson.fromJson<NotificationData>(it, NotificationData::class.java)?.id
//        }
//        NotificationModel(
//            title = title,
//            message = message,
//            type = type,
//            idContent = id
//        )
//    }
}