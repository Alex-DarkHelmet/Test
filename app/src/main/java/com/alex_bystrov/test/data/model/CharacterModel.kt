package com.alex_bystrov.test.data.model

import com.google.gson.annotations.SerializedName

data class CharacterModel(
    @SerializedName("id")
    val id: Int,

    @SerializedName("image")
    val imageCharacter: String = "",

    @SerializedName("name")
    val name: String = "",

    @SerializedName("status")
    val status: String = "",

    @SerializedName("species")
    val species: String = "",
)