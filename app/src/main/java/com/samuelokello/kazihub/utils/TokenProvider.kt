package com.samuelokello.kazihub.utils

import android.content.Context

fun getAccessToken(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)
    return sharedPreferences.getString("accessToken", null)
}
fun storeAccessToken(context: Context, token: String) {
    val sharedPreferences = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)
    with (sharedPreferences.edit()) {
        putString("accessToken", token)
        apply()
    }
}