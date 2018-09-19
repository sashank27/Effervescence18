package org.effervescence.app18

import android.app.Application
import android.widget.Toast

class Effervescence18 : Application() {

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "ApplicationStarted", Toast.LENGTH_SHORT).show()
    }
}