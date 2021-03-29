package com.nusantarian.deaflator.helper

import android.app.Application
import android.content.res.Resources

class App : Application() {

    companion object {
        lateinit var instance: Application
        lateinit var resource: Resources
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        resource = resources
    }
}