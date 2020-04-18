package org.wit.marshalmate.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fr_add.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.marshalmate.R
import org.wit.marshalmate.activities.MainActivity
import org.wit.marshalmate.models.EventModel
import org.wit.marshalmate.models.Location


class AddFragment : Fragment(),AnkoLogger,OnMapReadyCallback,GoogleMap.OnMarkerDragListener,GoogleMap.OnMarkerClickListener {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    var listOfPoints=ArrayList<Location>()
    var event =EventModel(0,"emptyId","empty","empty","empty",listOfPoints)
    private lateinit var googleMap:GoogleMap
    var location=Location(15.2,9.4,5f,"kevin howley")



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fr_add, container, false)


    }
    //map config
    override fun onMapReady(map: GoogleMap?) {
    }

    override fun onMarkerDragStart(marker: Marker) {
    }
    override fun onMarkerDrag(marker: Marker) {
    }
    override fun onMarkerDragEnd(marker: Marker) {
        location.lat = marker.position.latitude
        location.lng = marker.position.longitude
        location.zoom = googleMap.cameraPosition.zoom
    }
    override fun onMarkerClick(marker: Marker): Boolean{
        val loc = LatLng(location.lat, location.lng)
        marker.setSnippet("GPS : " + loc.toString())
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

    //handling logic here
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //you can set the title for your toolbar here for different fragments different titles
        activity!!.title = "Add Event"
        mapView.onCreate(savedInstanceState)
        (activity as MainActivity?)?.setUpMap()
        mapView.getMapAsync{
            googleMap=it
            googleMap.uiSettings.setZoomControlsEnabled(true)
            googleMap.setOnMarkerDragListener(this)
            googleMap.setOnMarkerClickListener(this)
            val loc= LatLng(52.347831,-7.18659)
            val options=MarkerOptions().title("Starting").snippet("TEstingSnippet " +loc.toString()).draggable(true).position(loc)
            googleMap.addMarker(options)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,13f))
        }
        addPointBtn.setOnClickListener{
            var pointName=pointNameText.text.toString()
            if (pointName.isNotEmpty()){
                info{"Point Name $pointName"}
                val loc= LatLng(52.347831,-7.26555)
                val options=MarkerOptions().title("added").snippet("Test2 " +loc.toString()).draggable(true).position(loc)
                googleMap.addMarker(options)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,13f))


            }
            else{
                Toast.makeText(context,"Please enter the point title",Toast.LENGTH_SHORT).show()

            }
           // if (numOfPointsEditText.)
        }

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


                listOfPoints.add(location)
                for(i in listOfPoints.indices){
                    info { "TestLocation....${listOfPoints[i]}" }
                }

                (activity as MainActivity).handleAddingEvents(event)
            }
            else{
                Toast.makeText(context,"Please fill out all fields",Toast.LENGTH_SHORT).show()
            }
        }


    }

}