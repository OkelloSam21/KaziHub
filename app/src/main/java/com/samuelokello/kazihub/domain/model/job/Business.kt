package com.samuelokello.kazihub.domain.model.job


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
    val lat: Int?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("lon")
    val lon: Int?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("user_id")
    val userId: Int?,
    @SerializedName("verification_code")
    val verificationCode: String?
)