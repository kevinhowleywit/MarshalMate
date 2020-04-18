package org.wit.hillfortapp.rooms


import androidx.room.*
import org.wit.marshalmate.models.EventModel

@Dao
interface eventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(event: EventModel)

    @Query("SELECT * FROM EventModel")
    fun findAll(): List<EventModel>

    @Query("select * from EventModel where id = :id")
    fun findById(id: Long): EventModel

    @Update
    fun update(event: EventModel)

    @Delete
    fun deleteHillfort(event: EventModel)
}
