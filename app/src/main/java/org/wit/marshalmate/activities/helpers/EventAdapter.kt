package org.wit.marshalmate.activities.helpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.event_card.view.*
import kotlinx.android.synthetic.main.user_card.view.*
import org.wit.marshalmate.R
import org.wit.marshalmate.models.EventModel
import org.wit.marshalmate.models.Person

interface EventListener{
    fun onEventClick(event: EventModel)
}

class EventAdapter constructor(private var events: ArrayList<EventModel>,private val listener: EventListener) :
    RecyclerView.Adapter<EventAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.event_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val event = events[holder.adapterPosition]
        holder.bind(event,listener)
    }

    override fun getItemCount(): Int = events.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(event: EventModel,listener: EventListener) {
            var date=""+event.day+"/"+event.month+"/"+event.year
            itemView.cardEventName.text=event.eventName
            itemView.cardEventDescription.text=event.description
            itemView.cardEventDate.text=date
            itemView.setOnClickListener{listener.onEventClick(event)}
        }
    }

}



















/*
import org.wit.marshalmate.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.event_card.view.*
import org.wit.marshalmate.main.MainApp
import org.wit.marshalmate.models.EventModel

interface EventListener {
    fun onEventClick(event: EventModel)
}
var app: MainApp? = null
var events=ArrayList<EventModel>()

class EventAdapter constructor(private var events: List<EventModel>,private val listener: EventListener) :
    RecyclerView.Adapter<EventAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.event_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val event = events[holder.adapterPosition]
        holder.bind(event/*,listener*/)
    }

    override fun getItemCount(): Int = events.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(event: EventModel/*,listener: EventListener*/) {
            itemView.cardEventName.text=event.eventName
            itemView.cardEventDescription.text=event.description
            //itemView.setOnClickListener{listener.onEventClick(event)}

        }
    }
}*/