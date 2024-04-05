//package com.samuelokello.kazihub.data.repository
//
//import android.util.Log
//import com.samuelokello.kazihub.data.model.sign_in.SignInRequest
//import com.samuelokello.kazihub.data.model.sign_in.SignInResponse
//import com.samuelokello.kazihub.data.model.sign_up.SignUpRequest
//import com.samuelokello.kazihub.data.model.sign_up.SignUpResponse
//import com.samuelokello.kazihub.data.remote.AuthApi
//import com.samuelokello.kazihub.utils.Resource
//import javax.inject.Inject
//
//class AuthRepository @Inject constructor(private val api: AuthApi) {
//    suspend fun signUp(signUpRequest: SignUpRequest):Resource<SignUpResponse> {
//        return try {
//            val response = api.signUp(signUpRequest)
//            Log.d("AuthRepository", "signUp: $response")
//            Resource.Success(response)
//        } catch (e: Exception) {
//            Resource.Error(e.message ?: "An error occurred")
//
//        }
//    }
//
//    suspend fun signIn(signInRequest: SignInRequest): Resource<SignInResponse> {
//        return try {
//            val response = api.sigIn(signInRequest)
//            Resource.Success(response)
//        } catch (e: Exception) {
//            Resource.Error(e.message ?: "An error occurred")
//        }
//    }
//
//}

package com.samuelokello.kazihub.data.repository

import android.util.Log
import com.samuelokello.kazihub.data.model.sign_in.SignInRequest
import com.samuelokello.kazihub.data.model.sign_in.SignInResponse
import com.samuelokello.kazihub.data.model.sign_up.SignUpRequest
import com.samuelokello.kazihub.data.model.sign_up.SignUpResponse
import com.samuelokello.kazihub.data.remote.AuthApi
import com.samuelokello.kazihub.utils.Resource
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api: AuthApi) {
    suspend fun signUp(signUpRequest: SignUpRequest):Resource<SignUpResponse> {
        return try {
            val response = api.signUp(signUpRequest)
            Log.d("AuthRepository", "signUp: $response")
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun signIn(signInRequest: SignInRequest): Resource<SignInResponse> {
        return try {
            val response = api.sigIn(signInRequest)
            Resource.Success(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun <T>handleException(e: Exception): Resource<T>{
        return if (e is HttpException && (e.code() == 401 || e.code() == 403)) {
            Resource.Error("Authorization failed. Please check your credentials.")
        } else {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}