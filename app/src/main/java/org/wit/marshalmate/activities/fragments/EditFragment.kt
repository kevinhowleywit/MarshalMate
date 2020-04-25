package org.wit.marshalmate.activities.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.enter_email_dialog.view.*
import kotlinx.android.synthetic.main.fr_edit.*
import kotlinx.android.synthetic.main.fr_search.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.marshalmate.R
import org.wit.marshalmate.activities.MainActivity
import org.wit.marshalmate.activities.helpers.EventAdapter

import org.wit.marshalmate.models.EventModel
import org.wit.marshalmate.models.PointProperties
import java.util.*
import kotlin.collections.ArrayList

class EditFragment : Fragment() , AnkoLogger, OnMapReadyCallback,GoogleMap.OnMarkerClickListener,GoogleMap.OnMarkerDragListener {
    var event=EventModel()
    private lateinit var googleMap: GoogleMap
    var location= PointProperties()
    private var assignedUser=""
    var listOfPoints=ArrayList<PointProperties>()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fr_edit, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "Edit"
        val bundle = arguments?.get("event")
        event = bundle as EventModel
        eventNameText.setText(event.eventName)
        eventDescription.setText(event.description)
        var default = LatLng(52.2474998, -7.1480493)

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            googleMap = it
            googleMap.uiSettings.setZoomControlsEnabled(true)
            googleMap.setOnMarkerClickListener(this)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(default, 13f))

            for (i in event.points) {
                var loc = LatLng(i.lat, i.lng)
                val options = MarkerOptions().title(i.assignedUser).snippet(" " + loc.toString())
                    .position(loc)
                googleMap.addMarker(options)

            }

        }

        chooseDateBtn.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePicker = DatePickerDialog(activity!!,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDayOfMonth ->
                    event.day = mDayOfMonth
                    event.month = mMonth
                    event.year = mYear
                }, year, month, day
            )
            datePicker.show()
        }
        addPointBtn.setOnClickListener {
            //alert dialog to enter email
            val mDialogView =
                LayoutInflater.from(context).inflate(R.layout.enter_email_dialog, null)
            val mBuilder = AlertDialog.Builder(context).setView(mDialogView).setTitle("Enter email")
            val mAlertDialog = mBuilder.show()
            mDialogView.dialogAddEmailBtn.setOnClickListener {
                assignedUser = mDialogView.dialogEmail.text.toString()
                if (assignedUser.isNotEmpty()) {
                    Toast.makeText(context, "This user will be assigned", Toast.LENGTH_SHORT).show()
                    mAlertDialog.dismiss()
                } else {
                    Toast.makeText(context, "Please enter a user email", Toast.LENGTH_SHORT).show()
                }

            }
            mDialogView.dialogCancelBtn.setOnClickListener {
                mAlertDialog.dismiss()
                Toast.makeText(context, "No assigned user for this point", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        saveChangesButton.setOnClickListener {
            event.eventName = eventNameText.text.toString()
            event.description = eventDescription.text.toString()
            event.points = listOfPoints
            (activity as MainActivity).handleUpdateEvent(event)


        }

    }

    override fun onMapReady(p0: GoogleMap?) {
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
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

    override fun onMarkerDragEnd(marker: Marker) {
        location.lat = marker.position.latitude
        location.lng = marker.position.longitude
        location.assignedUser=assignedUser
        listOfPoints.add(location.copy())
    }

    override fun onMarkerDragStart(marker: Marker) {
    }

    override fun onMarkerDrag(marker: Marker) {
    }


}