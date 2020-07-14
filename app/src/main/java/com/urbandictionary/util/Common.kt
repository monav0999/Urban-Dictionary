package com.urbandictionary.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.format.DateUtils
import androidx.appcompat.app.AlertDialog
import com.urbandictionary.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Common {
    companion object {
        /**
         * @param context Application context
         * @return If internet connection is available or not
         */
        fun isInternetConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            cm.run {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    getNetworkCapabilities(activeNetwork)?.run {
                        return hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                                || hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    }
                } else {
                    activeNetworkInfo?.run {
                        return when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            else -> false
                        }
                    }
                }
            }
            return false
        }

        /**
         * @param context Application context
         * @param message Error message
         */
        fun showErrorDialog(context: Context, message: String) {
            AlertDialog.Builder(context).setMessage(message)
                .setNeutralButton(context.getString(R.string.ALERT_BUTTON_CLOSE), null).show()
        }

        /**
         *
         * @param date date string received from API
         * @return Converted relative time span date string
         */
        fun getRelativeTimeSpanString(date: String?): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            sdf.timeZone = TimeZone.getTimeZone("GMT")
            var time: Long = 0
            try {
                time = sdf.parse(date).time
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            val now = System.currentTimeMillis()

            return DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
                .toString()
        }
    }
}