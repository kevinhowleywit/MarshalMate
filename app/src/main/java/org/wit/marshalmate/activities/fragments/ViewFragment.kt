package org.wit.marshalmate.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fr_view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.marshalmate.R
import org.wit.marshalmate.models.EventModel


class ViewFragment : Fragment() , AnkoLogger, OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {
    //private var events=ArrayList<EventModel>()
    var event=EventModel()
    private lateinit var googleMap:GoogleMap
    val user = FirebaseAuth.getInstance().currentUser
    val assignee=user?.email.toString()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "View"
        val bundle = arguments?.get("event")
        event= bundle as EventModel
        info { "VIEWFRAGMENT:${event.eventName }"}
        var loc=LatLng(52.2474998,-7.1480493)

        for (i in event.points) {
            if (i.assignedUser.equals(assignee)) {
                loc = LatLng(i.lat, i.lng)
            }
        }
        viewMapView.onCreate(savedInstanceState)
        viewMapView.getMapAsync{
            googleMap=it
            googleMap.uiSettings.setZoomControlsEnabled(true)
            googleMap.setOnMarkerClickListener(this)
            val options= MarkerOptions().title("Your assigned point").snippet(" " +loc.toString()).position(loc)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,13f))
            googleMap.addMarker(options)
        }
        eventNameViewText.text=event.eventName
        eventDescViewText.text=event.description
        var date=""+event.day+"/"+event.month+"/"+event.year
        eventDateViewText.text=date
    }

    override fun onMapReady(p0: GoogleMap?) {
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return false
    }
    override fun onDestroyView() {
        super.onDestroyView()
        viewMapView.onDestroy()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        viewMapView.onLowMemory()
    }
    override fun onPause() {
        super.onPause()
        viewMapView.onPause()
    }
    override fun onResume() {
        super.onResume()
        viewMapView.onResume()
    }


}