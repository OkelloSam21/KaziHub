package com.samuelokello.kazihub.data.model.profile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    @SerialName("bio")
    val bio: String,
    @SerialName("email")
    val email: String,
    @SerialName("id")
    val id: Int,
    @SerialName("img")
    val img: Any,
    @SerialName("is_verified")
    val isVerified: Boolean,
    @SerialName("joined_on")
    val joinedOn: String,
    @SerialName("lat")
    val lat: Double,
    @SerialName("location")
    val location: String,
    @SerialName("lon")
    val lon: Double,
    @SerialName("phone")
    val phone: String,
    @SerialName("user_id")
    val userId: Int,
    @SerialName("verification_code")
    val verificationCode: String
)