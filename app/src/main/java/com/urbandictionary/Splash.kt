package com.urbandictionary

import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class Splash : AppCompatActivity() {
    private var moHandler: Handler? = null
    private lateinit var moRunnable: Runnable
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val loWindow = window
        loWindow.setFormat(PixelFormat.RGBA_8888)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        startHome()
    }

    // Starts Dashboard screen after time set in SPLASH_TIME
    private fun startHome() {
        moHandler = Handler()
        moRunnable = Runnable {
            val loIntent = Intent(this@Splash, DictionaryList::class.java)
            startActivity(loIntent)
            finish()
        }
        moHandler?.postDelayed(moRunnable, SPLASH_TIME.toLong())
    }

    override fun onBackPressed() {
        if (moHandler != null) {
            moHandler!!.removeCallbacks(moRunnable)
        }
        super.onBackPressed()
    }

    companion object {
        private const val SPLASH_TIME = 2000
    }
}