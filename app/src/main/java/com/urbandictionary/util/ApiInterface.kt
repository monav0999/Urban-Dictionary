package com.urbandictionary.util

import com.urbandictionary.remote.Dictionary
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {

    /**
     * @param term word to search
     * @return list of dictionary from API
     */
    @Headers("X-RapidAPI-Key: " + AppConstants.API_KEY)
    @GET("/define")
    fun getList(@Query("term") term: String): Call<Dictionary>
}