package com.hanyeop.kakaosearch.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Document(
    @SerializedName("display_sitename")
    val sitename : String,
    val collection : String,
    val image_url : String,
    val datetime : Date
)
