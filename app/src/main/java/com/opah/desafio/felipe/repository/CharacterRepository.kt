package com.opah.desafio.felipe.repository

import com.opah.desafio.felipe.network.api.ApiService


class CharacterRepository(private val apiService: ApiService) {

    suspend fun getCharacters() = apiService.getCharacters().await()
}