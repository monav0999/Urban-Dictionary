package com.urbandictionary.remote

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Dictionary : Serializable {

    @SerializedName("list")
    var dictionary: List<Information>? = null
}