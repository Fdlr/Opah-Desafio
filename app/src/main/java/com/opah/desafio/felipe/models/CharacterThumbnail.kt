package com.opah.desafio.felipe.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterThumbnail(
    @Json(name = "path")
    var path: String,

    @Json(name = "extension")
    var extension: String
)