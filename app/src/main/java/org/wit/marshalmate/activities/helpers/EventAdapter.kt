package org.wit.marshalmate.activities.helpers
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
}