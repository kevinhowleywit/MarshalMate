package org.wit.marshalmate.activities
import android.content.Intent
import android.os.Bundle
import android.view.*
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fr_add.*
import kotlinx.android.synthetic.main.fr_search.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.wit.marshalmate.R
import org.wit.marshalmate.activities.fragments.HomeScreenFrag
import org.wit.marshalmate.activities.fragments.AddFragment
import org.wit.marshalmate.activities.fragments.SearchFragment
import org.wit.marshalmate.activities.helpers.EventAdapter
import org.wit.marshalmate.activities.helpers.EventListener
import org.wit.marshalmate.main.MainApp
import org.wit.marshalmate.models.EventModel
import org.wit.marshalmate.models.Location

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,AnkoLogger,EventListener {

    var app: MainApp? = null
    val LOCATION_REQUEST=2
    var event=EventModel()
    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        displaySelectedScreen(R.id.menu_home)




    }

    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_logout -> {
                Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                this.startActivity(intent)
                info { "logged out user" }
                this.finishAffinity()
            }
        }
        displaySelectedScreen(item.itemId)

        return true
    }


    private fun displaySelectedScreen(itemId: Int) {

        //creating fragment object
        var fragment: Fragment? = null

        //initializing the fragment object which is selected
        when (itemId) {
            R.id.menu_home -> fragment = HomeScreenFrag()
            R.id.menu_add -> fragment = AddFragment()
            R.id.menu_search -> fragment = SearchFragment()
        }

        //replacing the fragment
        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.content_frame, fragment)
            ft.commit()
        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
    }

    fun logTest() {
        info { "log test" }
    }




    fun configureCardView() {
        app = application as MainApp
        info { "in cofig card view" }
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = EventAdapter(app!!.events.findAll(), this)

    }

    fun updateCards() {
        info { "in  update cards" }
        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = EventAdapter(app!!.events.findAll(), this)
    }

    override fun onEventClick(event: EventModel) {
        //val intent = Intent(this, EditEventActivity::class.java)
        //startActivity(intent)

    }

    fun addPointsBtnHandler(event: EventModel) {
        //val location=Location(52.347831, -7.18659, 15f)
        info { "in add points handler" }
        val location = Location(52.245696, -7.139102, 15f)
        if (event.location.zoom != 0f) {
            location.lat =  event.location.lat
            location.lng = event.location.lng
            location.zoom = event.location.zoom
        }

        startActivity (intentFor<MapsActivity>().putExtra("location", LOCATION_REQUEST))
    }
    fun handleAddingEvents(event: EventModel) {
        app = application as MainApp
        //app!!.events.create(event.copy())
        info { "event added $event" }
        app!!.saveEvent(event)


        //setResult(AppCompatActivity.RESULT_OK)
        //finish()
    }

    fun configMap(){

        info { "in config map" }

    }
    /*
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
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }*/





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras?.getParcelable<Location>("location")!!
                    event.location.lat=location.lat
                    event.location.lng=location.lng
                    event.location.zoom=location.zoom



                }
            }
        }
    }


}

























