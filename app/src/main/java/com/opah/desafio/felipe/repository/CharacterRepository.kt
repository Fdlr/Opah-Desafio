package com.opah.desafio.felipe.repository

import com.opah.desafio.felipe.models.CharacterResults
import com.opah.desafio.felipe.models.HQ
import com.opah.desafio.felipe.network.api.ApiService
import com.orhanobut.hawk.Hawk

class CharacterRepository(private val apiService: ApiService) {

    suspend fun getCharacters() = apiService.getCharacters().await()

    suspend fun findComics(characterId: Int) = apiService.findComics(characterId).await()

    fun savePosition(characterResults: CharacterResults) {
        Hawk.put(CHARACTERRESULTS, characterResults)
    }

    fun getPosition(): CharacterResults? {
        return Hawk.get(CHARACTERRESULTS)
    }

    fun saveHQ(dataHQ: HQ) {
        Hawk.put(DATAHQ, dataHQ)
    }

    fun getDataHQ(): HQ? {
        return Hawk.get(DATAHQ)
    }

    companion object {
        const val CHARACTERRESULTS = "characterResults"
        const val DATAHQ = "dataHQ"
    }
}