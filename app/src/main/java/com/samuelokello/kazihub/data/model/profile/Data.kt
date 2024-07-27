package com.samuelokello.kazihub.data.model.profile


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("fname")
    val fname: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lname")
    val lname: String,
    @SerializedName("profile")
    val profile: Profile,
    @SerializedName("role")
    val role: String,
    @SerializedName("username")
    val username: String
)