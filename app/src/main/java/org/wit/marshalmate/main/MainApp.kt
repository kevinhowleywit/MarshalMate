package org.wit.marshalmate.main

import android.app.Application
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.marshalmate.models.*

class MainApp : Application(), AnkoLogger {

    lateinit var events:EventStore


    override fun onCreate() {
        super.onCreate()
        //events =EventMemStore()
        events=FirebaseStore(applicationContext)
        info("app is running")

    }

    fun saveEvent(event: EventModel) {
        info { "in add to realtime db:$event"  }
        val ref = FirebaseDatabase.getInstance().getReference("event")
        val eventId=ref.push().key
        if (eventId != null) {
            event.fbId=eventId
            ref.child(eventId).setValue(event).addOnCompleteListener{
                Toast.makeText(applicationContext,"Saved Successfully",Toast.LENGTH_SHORT).show()

            }
        }
    }

}