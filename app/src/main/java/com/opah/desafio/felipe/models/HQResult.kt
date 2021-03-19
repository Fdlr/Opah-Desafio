package com.opah.desafio.felipe.models

import com.google.gson.annotations.SerializedName
import retrofit2.http.Url
import java.io.Serializable


class HQResult : Serializable {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("urls")
    lateinit var urls: ArrayList<Url>

    @SerializedName("prices")
    lateinit var price: ArrayList<Prices>

}