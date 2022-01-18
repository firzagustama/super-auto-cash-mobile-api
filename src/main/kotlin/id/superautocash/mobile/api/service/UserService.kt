package id.superautocash.mobile.api.service

import id.superautocash.mobile.api.controller.request.UserLoginRequest
import id.superautocash.mobile.api.controller.request.UserRegisterRequest
import id.superautocash.mobile.api.controller.response.UserLoginResponse
import id.superautocash.mobile.api.controller.response.UserRegisterResponse

interface UserService {

    fun login(request: UserLoginRequest?): UserLoginResponse?

    fun register(request: UserRegisterRequest?): UserRegisterResponse?

}