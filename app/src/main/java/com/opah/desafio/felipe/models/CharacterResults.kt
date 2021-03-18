package com.opah.desafio.felipe.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterResults(
    @Json(name = "id")
    var characterId: Int,

    @Json(name = "name")
    var name: String,

    @Json(name = "description")
    var description: String,

    @Json(name = "thumbnail")
    var thumbnail: CharacterThumbnail
)