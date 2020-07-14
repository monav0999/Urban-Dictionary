package com.urbandictionary

import android.app.Application

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        @get:Synchronized
        var instance: GlobalApplication? = null
            private set
    }
}