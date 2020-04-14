package org.wit.marshalmate.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fr_add.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.marshalmate.R
import org.wit.marshalmate.activities.MainActivity
import org.wit.marshalmate.main.MainApp
import org.wit.marshalmate.models.EventModel


class AddFragment : Fragment(),AnkoLogger {

    var event=EventModel()
    var app:MainApp? =null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fr_add, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //you can set the title for your toolbar here for different fragments different titles
        activity!!.title = "Add Event"



        saveChangesButton.setOnClickListener{
            //testing communication between fragment and activity
            (activity as MainActivity?)?.logTest()

            event.eventName=eventNameText.text.toString()
            event.description=eventDescription.text.toString()

            //gets the logged in user from firebase and sets the creator to that email
            val user = FirebaseAuth.getInstance().currentUser
            event.creator=user?.email.toString()
            if (event.eventName.isNotEmpty() && event.description.isNotEmpty()){
                info { "add pressed: $event"}
                (activity as MainActivity).doAddToArrayList(event)
                }
            else{
                Toast.makeText(context,"Please fill out all fields",Toast.LENGTH_SHORT).show()
            }
        }

    }


}