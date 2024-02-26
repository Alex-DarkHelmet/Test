package com.alex_bystrov.test.data.remote.characters

import android.util.Log
import com.alex_bystrov.test.data.model.CharacterMapper
import com.alex_bystrov.test.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val api: CharactersApi
) : CharactersRepository {

    private val mapper = CharacterMapper()

    override suspend fun getCharactersByPage(page: Int): List<Character> {
        try {
            val response = api.getCharactersFromPage(page)
            return mapper.mapListCharacterModelToDomain(response.items)
            Log.i("CharacterResponse", "$response")
        } catch (e: Exception) {
            throw RuntimeException(e)
            Log.i("LoadingCharacters", "Failed fetching characters - $e")
        }
    }

    override suspend fun getCharacters(): Flow<List<Character>> {
//        try {
//            val response = api.getCharactersList().items
//            return mapper.mapListCharacterModelToDomain(response)
//        } catch (e: Exception) {
//            throw RuntimeException(e)
//        }

        return fetchAllPages()
    }

    override suspend fun getCharacterById(id: Int): Character {
        TODO("Not yet implemented")
    }

    private suspend fun testFetchAllPages(): List<Character> {
        val characters = mutableListOf<Character>()
        var counter = 1

        while (counter != TOTAL_PAGES + 1) {
            val response = api.getCharactersFromPage(counter).items
            val mappedCharacters = mapper.mapListCharacterModelToDomain(response)
            mappedCharacters.forEach { characters.add(it) }
            counter++
        }
        return characters
    }

    // make hot flow for emit data to repository
    private suspend fun initFetchingCharacters(): List<Character> {
        val response = api.getCharactersList()
        return mapper.mapListCharacterModelToDomain(response.items)
    }

    private suspend fun fetchAllPages(): Flow<List<Character>> {
        return flow {
            repeat(TOTAL_PAGES) {
                val response = api.getCharactersFromPage(it)
                emit(mapper.mapListCharacterModelToDomain(response.items))
            }
        }
    }

    companion object {
        private const val TOTAL_PAGES = 42
    }
}