package org.wit.marshalmate.models

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.Marker


@Parcelize
@Entity
data class EventModel(@PrimaryKey(autoGenerate = true)
                      var id:Long=0,
                      var fbId : String = "",
                      var creator:String="",
                      var eventName:String="",
                      var description:String=""
                      //var image:String="",
                      //@Embedded var points : MutableList<Location>
): Parcelable {
}

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f,
                    var assignedUser:String=""): Parcelable



