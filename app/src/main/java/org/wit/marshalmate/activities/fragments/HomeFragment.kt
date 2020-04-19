package org.wit.marshalmate.activities.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fr_home_screen.*
import kotlinx.android.synthetic.main.fr_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

import org.wit.marshalmate.R
import org.wit.marshalmate.activities.MainActivity
import org.wit.marshalmate.activities.helpers.EventAdapter
import org.wit.marshalmate.activities.helpers.EventListener

import org.wit.marshalmate.models.EventModel

private var events=ArrayList<EventModel>()

private var ownedEvents=ArrayList<EventModel>()
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
        for (i in events){
            if(i.creator.equals(owner)){
                ownedEvents.add(i)
            }
        }




        val mEventAdapter=EventAdapter(ownedEvents,this)
        ownedEventsRecylcerView.layoutManager=LinearLayoutManager(context)
        ownedEventsRecylcerView.adapter=mEventAdapter


    }

    override fun onEventClick(event: EventModel) {
        if (event.creator.equals(owner)){
            Toast.makeText(context,"This is an owned event", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context,"Not Owned", Toast.LENGTH_SHORT).show()
        }

    }
}
