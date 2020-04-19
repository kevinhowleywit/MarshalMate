package org.wit.marshalmate.activities.helpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_card.view.*
import org.wit.marshalmate.R
import org.wit.marshalmate.models.Person

interface UserListener{
    fun onUserClick(person: Person)
}

class UserAdapter constructor(private var users: ArrayList<Person>,private val listener: UserListener) :
    RecyclerView.Adapter<UserAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.user_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val user = users[holder.adapterPosition]
        holder.bind(user,listener)
    }

    override fun getItemCount(): Int = users.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: Person,listener: UserListener) {
            itemView.cardUserEmail.text=user.mail
            itemView.setOnClickListener{listener.onUserClick(user)}
        }
    }

}


/**
 * package org.wit.marshalmate.activities.helpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_card.view.*
import org.wit.marshalmate.R
import org.wit.marshalmate.models.Person

class UserAdapter(val users:ArrayList<Person>,val context:Context):
RecyclerView.Adapter<UserAdapter.ViewHolder>() {

class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
fun bindItems(person: Person){
itemView.cardUserEmail.text=person.mail

}
}

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
val v =LayoutInflater.from(parent.context).inflate(R.layout.user_card,parent,false)
return ViewHolder(v)

}

override fun getItemCount(): Int {
return users.size

}

override fun onBindViewHolder(holder: ViewHolder, position: Int) {
holder.bindItems(users[position])

holder.itemView.setOnClickListener{

}

}
}
 */
