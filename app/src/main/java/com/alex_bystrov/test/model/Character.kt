package com.alex_bystrov.test.model

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("status")
    val status: String = "",

    @SerializedName("species")
    val species: String = "",

    @SerializedName("gender")
    val gender: String = "",

    @SerializedName("origin")
    val origin: Origin? = Origin(),

    @SerializedName("location")
    val location: Location? = Location(),

    @SerializedName("image")
    val imageCharacter: String = "",
)

data class Origin(val name: String = "", val url: String = "")

data class Location(val name: String = "", val url: String = "")