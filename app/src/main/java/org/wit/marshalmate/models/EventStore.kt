package org.wit.marshalmate.models

interface EventStore{
    fun findAll(): List<EventModel>
    fun create(event:EventModel)
    fun update(event: EventModel)
}