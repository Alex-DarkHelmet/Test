package com.alex_bystrov.test.data.remote.characters

import com.alex_bystrov.test.model.Character
import com.google.gson.annotations.SerializedName

data class CharactersListResponse(
    @SerializedName("results")
    val items: List<Character> = emptyList()
)
