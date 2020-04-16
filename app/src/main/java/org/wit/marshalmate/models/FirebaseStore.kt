package org.wit.marshalmate.models

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import java.io.ByteArrayOutputStream
import java.io.File

class FirebaseStore(val context: Context) : EventStore, AnkoLogger {

    val events = ArrayList<EventModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

    override fun findAll(): List<EventModel> {
        return events
    }

    /*override fun findById(id: Long): EventModel? {
        val foundEvent: EventModel? = events.find { p -> p.id == id }
        return foundEvent
    }*/

    override fun create(event: EventModel) {
        val key = db.child("users").child(userId).child("events").push().key
        key?.let {
            event.fbId = key
            events.add(event)
            db.child("users").child(userId).child("events").child(key).setValue(event)
        }
    }

    override fun update(event: EventModel) {
        var foundEvent: EventModel? = events.find { p -> p.fbId == event.fbId }
        if (foundEvent != null) {
            foundEvent.eventName = event.eventName
            foundEvent.description = event.description
            foundEvent.location = event.location
        }

        db.child("users").child(userId).child("events").child(event.fbId).setValue(event)

    }

    /*override fun delete(event: EventModel) {
        db.child("users").child(userId).child("events").child(event.fbId).removeValue()
        events.remove(event)
    }*/

   /* override fun clear() {
        events.clear()
    }*/



    fun fetchevents(eventsReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(events) { it.getValue<EventModel>(EventModel::class.java) }
                eventsReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        st = FirebaseStorage.getInstance().reference
        events.clear()
        db.child("users").child(userId).child("events").addListenerForSingleValueEvent(valueEventListener)
    }
}