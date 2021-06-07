package com.hanyeop.kakaosearch.model

import com.google.gson.annotations.SerializedName

data class ImageSearchResponse(
    @SerializedName("meta")
    val metaData: MetaData?,

    @SerializedName("documents")
    var documents: MutableList<Document>?
)
