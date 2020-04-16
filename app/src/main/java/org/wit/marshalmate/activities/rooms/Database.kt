package org.wit.hillfortapp.rooms

import androidx.room.Database
import androidx.room.RoomDatabase
import org.wit.marshalmate.models.EventModel

@Database(entities = arrayOf(EventModel::class), version = 1,  exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun eventDao(): eventDao
}