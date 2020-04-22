package org.wit.marshalmate.activities.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
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
import kotlinx.android.synthetic.main.enter_email_dialog.*
import kotlinx.android.synthetic.main.enter_email_dialog.view.*
import kotlinx.android.synthetic.main.fr_add.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.marshalmate.R
import org.wit.marshalmate.activities.MainActivity
import org.wit.marshalmate.models.EventModel
import org.wit.marshalmate.models.PointProperties
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddFragment : Fragment(),AnkoLogger,OnMapReadyCallback,GoogleMap.OnMarkerDragListener,GoogleMap.OnMarkerClickListener {

    //variables for the class
    var formate = SimpleDateFormat("dd MMM, YYYY",Locale.UK)
    private lateinit var lastLocation: Location
    var listOfPoints=ArrayList<PointProperties>()
    var event =EventModel()
    private lateinit var googleMap:GoogleMap
    var location=PointProperties()
    private var assignedUser=""




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //returning layout file
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
        //sets the list
        location.lat = marker.position.latitude
        location.lng = marker.position.longitude
        location.assignedUser=assignedUser
        listOfPoints.add(location.copy())
    }
    override fun onMarkerClick(marker: Marker): Boolean{
        val loc = LatLng(location.lat, location.lng)
        marker.setSnippet("GPS : " + loc.toString())
        return false
    }
    override fun onDestroyView() {
        super.onDestroyView()
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
        //configuring map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync{
            googleMap=it
            googleMap.uiSettings.setZoomControlsEnabled(true)
            googleMap.setOnMarkerDragListener(this)
            googleMap.setOnMarkerClickListener(this)
            val loc= LatLng(52.2474998,-7.1480493)
            val options=MarkerOptions().title("Starting").snippet("TestingSnippet " +loc.toString()).draggable(true).position(loc)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,13f))
        }
        //creates date pop up and adds date
        chooseDateBtn.setOnClickListener{
            val c =Calendar.getInstance()
            val year=c.get(Calendar.YEAR)
            val month=c.get(Calendar.MONTH)
            val day=c.get(Calendar.DAY_OF_MONTH)
            val datePicker=DatePickerDialog(activity!!,DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDayOfMonth ->
                event.day=mDayOfMonth
                event.month=mMonth
                event.year=mYear
            },year,month,day)
            datePicker.show()
        }
        //handles the button to add a point to the map
        addPointBtn.setOnClickListener{
            //alert dialog to enter email
            val mDialogView=LayoutInflater.from(context).inflate(R.layout.enter_email_dialog,null)
            val mBuilder=AlertDialog.Builder(context).setView(mDialogView).setTitle("Enter email")
            val mAlertDialog=mBuilder.show()
            mDialogView.dialogAddEmailBtn.setOnClickListener {
                assignedUser=mDialogView.dialogEmail.text.toString()
                if(assignedUser.isNotEmpty()){
                    Toast.makeText(context,"This user will be assigned",Toast.LENGTH_SHORT).show()
                    mAlertDialog.dismiss()
                }
                else{
                    Toast.makeText(context,"Please enter a user email",Toast.LENGTH_SHORT).show()
                }

            }
            mDialogView.dialogCancelBtn.setOnClickListener {
                mAlertDialog.dismiss()
                Toast.makeText(context,"No assigned user for this point",Toast.LENGTH_SHORT).show()
            }
            //Point Name Handling
            var pointName=pointNameText.text.toString()
            if (pointName.isNotEmpty()){
                info{"Point Name $pointName"}
                val loc= LatLng(52.2474998,-7.1480493)
                val options=MarkerOptions().title(pointName ).snippet("marker " +loc.toString()).draggable(true).position(loc)
                googleMap.addMarker(options)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,13f))
                pointNameText.setText("")
            }
            else{
                Toast.makeText(context,"Please enter the point title",Toast.LENGTH_SHORT).show()
            }
        }
        //saves the event
        saveChangesButton.setOnClickListener{
            event.eventName=eventNameText.text.toString()
            event.description=eventDescription.text.toString()
            event.points=listOfPoints

            //gets the logged in user from firebase and sets the creator to that email
            val user = FirebaseAuth.getInstance().currentUser
            event.creator=user?.email.toString()
            if (event.year!=0){
                if (event.eventName.isNotEmpty() && event.description.isNotEmpty()){
                    info { "add pressed: $event"}

                    //passes the event to the main activity
                    //where it is then fed to MainApp to save
                    (activity as MainActivity).handleAddingEvents(event)
                }
                else{
                    Toast.makeText(context,"Please fill out all fields",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(context,"Please enter a date",Toast.LENGTH_SHORT).show()
            }
        }
    }

}