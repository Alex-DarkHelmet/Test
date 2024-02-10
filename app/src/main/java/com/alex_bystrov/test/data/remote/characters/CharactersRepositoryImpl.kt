package com.alex_bystrov.test.data.remote.characters

import android.util.Log
import coil.request.ImageRequest
import com.alex_bystrov.test.data.remote.RetrofitClient
import com.alex_bystrov.test.model.Character
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.ServerSocket
import java.util.logging.SocketHandler

class CharactersRepositoryImpl{
    private val api = RetrofitClient.characterResponse()

    private val _characters: MutableStateFlow<List<Character>> = MutableStateFlow(
        emptyList()
    )
    val characters: StateFlow<List<Character>> = _characters

    suspend fun fetchCertainCharacter() {
        try {
            val response = api.getCharacter(1)
            _characters.emit(listOf(response))
        } catch (e: Exception) {
            Log.i("FailedLoadingData", "Exception - $e")
        }
    }

    suspend fun fetchCharacters() {
        try {
            val response = api.getCharactersList()
            _characters.emit(response.items)
        } catch (e: Exception) {
            Log.i("LoadingCharacters", "Exception - $e")
        }
    }

    suspend fun fetchCharactersFromPage(page: Int) {
        try {
            val response = api.getCharactersFromPage(page)
            Log.i("CharacterResponse", "$response")
            _characters.emit(response.items)
        } catch (e: Exception) {
            Log.i("LoadingCharacters", "Failed fetching characters - $e")
        }
    }
}