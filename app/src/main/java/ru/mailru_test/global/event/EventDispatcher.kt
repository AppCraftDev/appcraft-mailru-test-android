package ru.mailru_test.global.event

import android.os.Handler

typealias EventListener = (code:Int, data:Any?) -> Unit

class EventDispatcher {
    private val eventHandlers = mutableMapOf<Int, MutableList<EventListener>>()

    fun addEventListener(eventCode: Int, listener: EventListener): EventListener {
        if (!eventHandlers.containsKey(eventCode))
            eventHandlers[eventCode] = mutableListOf()
        eventHandlers[eventCode]?.run {
            if (!contains(listener))
                add(listener)
        }

        return listener
    }

    fun removeEventListener(listener: EventListener) = eventHandlers
        .map { handler -> handler.value }
        .filter { it.any() }
        .forEach { it.remove(listener) }

    fun sendEvent(eventCode: Int, data: Any? = null) = eventHandlers
        .filter { it.key == eventCode && it.value.any() }
        .map { it.value }
        .flatten()
        .forEach { listener -> Handler().post { listener(eventCode, data) } }

}