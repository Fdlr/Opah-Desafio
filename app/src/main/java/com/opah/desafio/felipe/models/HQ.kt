package com.opah.desafio.felipe.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class HQ : Serializable {
    @SerializedName("code")
    var code: Int = 0
    @SerializedName("status")
    var status: String = ""
    @SerializedName("etag")
    var etag: String = ""
    @SerializedName("data")
    lateinit var data: DataHQ
}