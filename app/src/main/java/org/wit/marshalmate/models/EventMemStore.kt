package org.wit.marshalmate.models
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId=0L
internal fun getId():Long{
    return lastId++
}

class EventMemStore :EventStore,AnkoLogger{

    val events= ArrayList<EventModel>()
    override fun findAll(): List<EventModel> {
        return events

    }

    override fun create(event: EventModel) {
        event.id= getId()
        events.add(event)
        logAllEvents()
    }
    fun logAllEvents(){
        events.forEach{info("$it")}
    }
    override fun update(event: EventModel){
        var foundEvent:EventModel?=events.find { p -> p.id ==event.id }
        if(foundEvent !=null){
            foundEvent.eventName=event.eventName
            foundEvent.description=event.description
            logAllEvents()
        }
    }

}