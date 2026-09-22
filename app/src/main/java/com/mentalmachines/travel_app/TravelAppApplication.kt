package com.mentalmachines.travel_app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TravelAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
