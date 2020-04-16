package org.wit.marshalmate.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.marshalmate.activities.helpers.exists
import org.wit.marshalmate.activities.helpers.read
import org.wit.marshalmate.activities.helpers.write

import java.util.*

val JSON_FILE = "events.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<EventModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class EventJSONStore : EventStore, AnkoLogger {

    val context: Context
    var events = mutableListOf<EventModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<EventModel> {
        return events
    }

    override fun create(event: EventModel) {
        event.id = generateRandomId()
        events.add(event)
        serialize()
    }


    override fun update(event: EventModel) {
        // todo
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(events, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        events = Gson().fromJson(jsonString, listType)
    }
}