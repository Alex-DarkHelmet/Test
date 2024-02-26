package com.alex_bystrov.test.data.remote.characters

import com.alex_bystrov.test.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    suspend fun getCharactersByPage(page: Int): List<Character>

    suspend fun getCharacters(): Flow<List<Character>>

    suspend fun getCharacterById(id: Int): Character
}