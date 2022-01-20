package id.superautocash.mobile.api.controller

import id.superautocash.mobile.api.controller.request.ApiRequest
import id.superautocash.mobile.api.controller.request.UserExistsRequest
import id.superautocash.mobile.api.controller.request.UserLoginRequest
import id.superautocash.mobile.api.controller.request.UserRegisterRequest
import id.superautocash.mobile.api.controller.response.ApiResponse
import id.superautocash.mobile.api.controller.response.UserExistsResponse
import id.superautocash.mobile.api.controller.response.UserLoginResponse
import id.superautocash.mobile.api.controller.response.UserRegisterResponse
import id.superautocash.mobile.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {
    @Autowired
    lateinit var service: UserService

    @PostMapping(
        value = ["/login"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun login(@RequestBody request: ApiRequest<UserLoginRequest>): ApiResponse<UserLoginResponse> {
        return ApiResponse(
            success = true,
            data = service.login(request.data)
        )
    }

    @PostMapping(
        value = ["/register"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun register(@RequestBody request: ApiRequest<UserRegisterRequest>): ApiResponse<UserRegisterResponse> {
        return ApiResponse(
            success = true,
            data = service.register(request.data)
        )
    }

    @PostMapping(
        value = ["/user/check"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun userCheck(@RequestBody request: ApiRequest<UserExistsRequest>): ApiResponse<UserExistsResponse> {
        return ApiResponse(
            success = true,
            data = service.userCheck(request.data)
        )
    }
}