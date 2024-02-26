package com.alex_bystrov.test.data.remote.characters

import com.alex_bystrov.test.data.model.CharacterModel
import com.alex_bystrov.test.data.model.CharactersListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {

    @GET("./character")
    suspend fun getCharactersList(): CharactersListResponse

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id : Int): CharacterModel

    @GET("./character/")
    suspend fun getCharactersFromPage(@Query("page") page: Int): CharactersListResponse
}