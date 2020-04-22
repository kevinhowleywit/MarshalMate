package org.wit.marshalmate.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fr_view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.marshalmate.R
import org.wit.marshalmate.models.EventModel


class ViewFragment : Fragment() , AnkoLogger {
    //private var events=ArrayList<EventModel>()
    var event=EventModel()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_view, container, false)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "View"

        val bundle = arguments?.get("event")
        event= bundle as EventModel
        info { "VIEWFRAGMENT:${event.eventName }"}

        eventNameViewText.text=event.eventName


    }




}