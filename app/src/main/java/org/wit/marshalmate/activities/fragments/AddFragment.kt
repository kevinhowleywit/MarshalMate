package org.wit.marshalmate.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fr_add.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import com.google.android.gms.maps.SupportMapFragment

import org.wit.marshalmate.R
import org.wit.marshalmate.activities.MainActivity
import org.wit.marshalmate.main.MainApp
import org.wit.marshalmate.models.EventModel


class AddFragment : Fragment(),AnkoLogger,OnMapReadyCallback,GoogleMap.OnMarkerDragListener,GoogleMap.OnMarkerClickListener {

    var event=EventModel()
    private lateinit var googleMap:GoogleMap


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fr_add, container, false)


    }
    override fun onMapReady(map: GoogleMap?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMarkerDragStart(marker: Marker) {
    }
    override fun onMarkerDrag(marker: Marker) {
    }
    override fun onMarkerDragEnd(marker: Marker) {
    }
    override fun onMarkerClick(marker: Marker): Boolean{
        return false
    }
    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //you can set the title for your toolbar here for different fragments different titles
        activity!!.title = "Add Event"
        (activity as MainActivity).configMap()
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync{
            googleMap=it
            googleMap.setOnMarkerDragListener(this)
            googleMap.setOnMarkerClickListener(this)
        } 



        /*addPointsBtn.setOnClickListener{
            info { "pressed add points" }
            //easier to handle the button press in the parent activity
            (activity as MainActivity).addPointsBtnHandler(event)
        }*/


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
                (activity as MainActivity).handleAddingEvents(event)
            }
            else{
                Toast.makeText(context,"Please fill out all fields",Toast.LENGTH_SHORT).show()
            }
        }


    }
    fun test(){

    }




}