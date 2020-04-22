package org.wit.marshalmate.main

import android.app.Application
import android.widget.Toast
import com.google.firebase.database.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.marshalmate.models.*



class MainApp : Application(), AnkoLogger {

    var events=ArrayList<EventModel>()
    var people=ArrayList<Person>()
    lateinit var ref:DatabaseReference


    override fun onCreate() {
        super.onCreate()
        info("app is running")

    }

    fun addUserToDB(person: Person){
        info { "in add to realtime db:$person"  }
        val ref = FirebaseDatabase.getInstance().getReference("person")
        val personId=ref.push().key
        if (personId != null) {
            person.fbId=personId
            ref.child(personId).setValue(person).addOnCompleteListener{
                Toast.makeText(applicationContext,"Your email has been added to the database",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun saveEvent(event: EventModel) {
        info { "in add to realtime db:$event"  }
        ref = FirebaseDatabase.getInstance().getReference("event")
        val eventId=ref.push().key
        if (eventId != null) {
            event.fbId=eventId
            ref.child(eventId).setValue(event).addOnCompleteListener{
                Toast.makeText(applicationContext,"Saved Successfully",Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun fetchAllEvents():ArrayList<EventModel>{
        ref = FirebaseDatabase.getInstance().getReference("event")
        ref.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                events.clear()
                if(p0!!.exists()){
                    for(i in p0.children){
                        val event = i.getValue(EventModel::class.java)
                        events.add(event!!)
                        info{"Fetching event:$event"}
                    }
                }

            }

        })
        return events
    }

    fun fetchOwnedEvents(owner: String):ArrayList<EventModel>{
        var fetchownedevents=fetchAllEvents()
        var ownedEvents=ArrayList<EventModel>()
        ownedEvents.clear()
        for (i in fetchownedevents){
            if(i.creator.equals(owner)){
                ownedEvents.add(i.copy())
            }
        }
        return ownedEvents
    }
    fun fetchPartOfEvents(owner: String):ArrayList<EventModel>{
        info { "fetchPartOfEvents" }
        var allEvents=fetchAllEvents()
        var fetchPartOfEvents=ArrayList<EventModel>()
        fetchPartOfEvents.clear()
        for(i in allEvents){
            for(j in i.points){
                if(j.assignedUser.equals(owner)){
                    fetchPartOfEvents.add(i)
                }
            }
        }
        return fetchPartOfEvents

    }
    fun fetchAllUsers(): ArrayList<Person> {
        ref = FirebaseDatabase.getInstance().getReference("person")
        ref.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                people.clear()
                if(p0!!.exists()){
                    for(i in p0.children){
                        val person = i.getValue(Person::class.java)
                        people.add(person!!)
                        info{"Fetching users:$person"}
                    }
                }
            }
        })
        return people
    }







}