package org.wit.marshalmate.models

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.Marker
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

var listOfPoints=ArrayList<PointProperties>()
val c =Calendar.getInstance()
val mYear=c.get(Calendar.YEAR)
val mMonth=c.get(Calendar.MONTH)
val mDay=c.get(Calendar.DAY_OF_MONTH)

@Parcelize
@Entity
data class EventModel(@PrimaryKey(autoGenerate = true)
                        var id:Long=0,
                        var fbId : String = "",
                        var creator:String="",
                        var eventName:String="",
                        var description:String="",
                        var year:Int=0,
                        var month:Int=0,
                        var day:Int=0,
                        @Embedded
                        var points : ArrayList<PointProperties>): Parcelable{constructor():this(0,"emptyId","empty","empty","empty",0,0,0,listOfPoints)}


@Parcelize
data class PointProperties  (   var lat: Double = 0.0,
                                var lng: Double = 0.0,
                                var zoom: Float = 0f,
                                var assignedUser:String=""): Parcelable
@Parcelize
data class Person(var fbId : String = "",
                  var mail:String=""):Parcelable




