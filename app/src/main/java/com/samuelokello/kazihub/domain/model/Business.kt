package com.samuelokello.kazihub.domain.model


import com.google.gson.annotations.SerializedName

data class Business(
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("img")
    val img: Any?,
    @SerializedName("is_verified")
    val isVerified: Boolean?,
    @SerializedName("joined_on")
    val joinedOn: String?,
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("lon")
    val lon: Double?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("verification_code")
    val verificationCode: String?
)