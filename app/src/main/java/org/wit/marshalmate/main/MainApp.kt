package org.wit.marshalmate.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.marshalmate.models.EventMemStore
import org.wit.marshalmate.models.EventModel

class MainApp : Application(), AnkoLogger {
    val events =EventMemStore()
    override fun onCreate() {
        super.onCreate()
        info("app is running")
        




    }
}