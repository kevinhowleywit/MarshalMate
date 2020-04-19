package org.wit.marshalmate.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fr_search.*
import org.wit.marshalmate.R
import org.wit.marshalmate.activities.MainActivity
import org.wit.marshalmate.activities.helpers.EventAdapter
import org.wit.marshalmate.activities.helpers.EventListener
import org.wit.marshalmate.models.EventModel

class SearchFragment : Fragment() ,AnkoLogger,EventListener{
    private var events=ArrayList<EventModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_search, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "Search"

        events=(activity as MainActivity).getAllEvents()
        events.forEach({info { "LOGGING FROM ALL EVENTS$it" }})
        val mEventAdapter=EventAdapter(events,this)
        allEventsRecyclerView.layoutManager=LinearLayoutManager(context)
        allEventsRecyclerView.adapter=mEventAdapter

    }

    override fun onEventClick(event: EventModel) {
        Toast.makeText(context,"${event.eventName}", Toast.LENGTH_SHORT).show()

    }


}