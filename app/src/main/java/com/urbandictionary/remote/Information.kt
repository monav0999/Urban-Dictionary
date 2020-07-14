package com.urbandictionary.remote

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Information : Serializable {

    @SerializedName("word")
    var title: String? = null

    @SerializedName("author")
    var author: String? = null

    @SerializedName("definition")
    var description: String? = null

    @SerializedName("example")
    var example: String? = null

    @SerializedName("written_on")
    var date: String? = null

    @SerializedName("thumbs_up")
    var thumbsUp: Int = 0

    @SerializedName("thumbs_down")
    var thumbsDown: Int = 0
}