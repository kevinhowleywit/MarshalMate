package org.wit.marshalmate.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fr_home_screen.*
import org.jetbrains.anko.AnkoLogger
import org.wit.marshalmate.R
import org.wit.marshalmate.activities.MainActivity
import org.wit.marshalmate.activities.helpers.EventAdapter
import org.wit.marshalmate.activities.helpers.EventListener
import org.wit.marshalmate.models.EventModel


private var events=ArrayList<EventModel>()
private var ownedEvents=ArrayList<EventModel>()
private var eventPartOf=ArrayList<EventModel>()

val user = FirebaseAuth.getInstance().currentUser
val owner=user?.email.toString()


class HomeFragment : Fragment(),AnkoLogger,EventListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_home_screen, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "Home"

        //getting Owned Events only
        events=(activity as MainActivity).getAllEvents()
        ownedEvents.clear()
        for (i in events){
            if(i.creator.equals(owner)){
                ownedEvents.add(i.copy())
            }
        }
        //getting events that the user is a part of
        eventPartOf.clear()
        for(i in events){
            for(j in i.points){
                if(j.assignedUser.equals(owner)){
                    eventPartOf.add(i)
                }
            }
        }

        //duplicate remover
        val tempOwned= ownedEvents
        val tempPartOf= eventPartOf

        //pass the events to recycler views
        val mEventAdapter=EventAdapter(ownedEvents,this)
        ownedEventsRecylcerView.layoutManager=LinearLayoutManager(context)
        ownedEventsRecylcerView.adapter=mEventAdapter

        val nEventAdapter=EventAdapter(eventPartOf,this)
        upcomingEventsRecyclerView.layoutManager=LinearLayoutManager(context)
        upcomingEventsRecyclerView.adapter=nEventAdapter




    }

    fun removeDuplicates(){

    }

    override fun onEventClick(event: EventModel) {
        if (event.creator.equals(owner)){
            Toast.makeText(context,"This is an owned event", Toast.LENGTH_SHORT).show()
            val t: FragmentTransaction = this.fragmentManager!!.beginTransaction()
            val mFrag: Fragment = EditFragment()
            t.replace(R.id.content_frame, mFrag)
            t.commit()

        }
        else{
            Toast.makeText(context,"Not Owned", Toast.LENGTH_SHORT).show()

            val t: FragmentTransaction = this.fragmentManager!!.beginTransaction()
            val mFrag: Fragment = ViewFragment()
            t.replace(R.id.content_frame, mFrag)
            t.commit()
        }

    }





}
