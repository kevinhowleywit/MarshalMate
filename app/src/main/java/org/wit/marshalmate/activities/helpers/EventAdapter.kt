package org.wit.marshalmate.activities.helpers
import org.wit.marshalmate.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.event_card.view.*
import org.wit.marshalmate.models.EventModel

class EventAdapter constructor(private var events: List<EventModel>) :
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
        holder.bind(event)
    }

    override fun getItemCount(): Int = events.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(event: EventModel) {
            itemView.cardEventName.text=event.eventName
            itemView.cardEventDescription.text=event.description

        }
    }
}