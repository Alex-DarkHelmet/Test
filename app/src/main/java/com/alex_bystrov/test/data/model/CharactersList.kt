package com.alex_bystrov.test.data.model

import com.google.gson.annotations.SerializedName

data class CharactersListResponse(
    @SerializedName("results")
    val items: List<CharacterModel> = emptyList()
)
