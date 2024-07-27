package com.samuelokello.kazihub.data.model.profile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("fname")
    val fname: String,
    @SerialName("id")
    val id: Int,
    @SerialName("lname")
    val lname: String,
    @SerialName("profile")
    val profile: Profile,
    @SerialName("role")
    val role: String,
    @SerialName("username")
    val username: String
)