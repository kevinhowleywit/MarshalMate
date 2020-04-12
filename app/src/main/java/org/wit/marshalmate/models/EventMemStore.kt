package org.wit.marshalmate.models
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class EventMemStore :EventStore,AnkoLogger{

    val events= ArrayList<EventModel>()
    override fun findAll(): List<EventModel> {
        return events

    }

    override fun create(event: EventModel) {
        events.add(event)
    }
    fun logAllEvents(){
        events.forEach{info("$it")}
    }

}