//package com.example.network.models.remote.signup
//
//import com.samuelokello.kazihub.presentation.shared.authentication.sign_up.SignUpState
//
//class SignUpMapper {
//    fun mapSignUpStateToSignUpData(signUpState: SignUpState): SignUpRequest {
//        return SignUpRequest(
//            username = signUpState.userName,
//            fname = signUpState.firstName,
//            lname = signUpState.lastName,
//            role = signUpState.role,
//            password = signUpState.password
//        )
//    }
//
//    fun mapSignUpResponseToUserData(signUpResponse: SignUpResponse): UserData {
//        return UserData(
//            username = signUpResponse.data.username,
//            fname = signUpResponse.data.fname,
//            lname = signUpResponse.data.lname,
//            role = signUpResponse.data.role,
//            id = signUpResponse.data.id
//        )
//    }
//
//    fun mapSignUpStateToRequest(signUpState: SignUpState): SignUpRequest {
//        return SignUpRequest(
//            username = signUpState.userName,
//            fname = signUpState.firstName,
//            lname = signUpState.lastName,
//            role = signUpState.role,
//            password = signUpState.password
//        )
//    }
//}