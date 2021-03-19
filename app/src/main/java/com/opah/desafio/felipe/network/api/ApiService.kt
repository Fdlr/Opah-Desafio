package com.opah.desafio.felipe.network.api

import com.opah.desafio.felipe.models.CharacterResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("v1/public/characters")
    fun getCharacters(): Deferred<Response<CharacterResponse>>
}