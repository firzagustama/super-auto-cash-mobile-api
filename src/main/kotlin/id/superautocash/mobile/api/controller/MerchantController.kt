package id.superautocash.mobile.api.controller

import id.superautocash.mobile.api.controller.request.ApiRequest
import id.superautocash.mobile.api.controller.request.MerchantLoginRequest
import id.superautocash.mobile.api.controller.request.MerchantRegisterRequest
import id.superautocash.mobile.api.controller.response.ApiResponse
import id.superautocash.mobile.api.controller.response.MerchantLoginResponse
import id.superautocash.mobile.api.controller.response.MerchantRegisterResponse
import id.superautocash.mobile.api.service.MerchantService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["merchant"])
class MerchantController @Autowired constructor(
    val service: MerchantService
) {

    @PostMapping(
        value = ["/login"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun login(@RequestBody request: ApiRequest<MerchantLoginRequest>): ApiResponse<MerchantLoginResponse> {
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
    fun register(@RequestBody request: ApiRequest<MerchantRegisterRequest>): ApiResponse<MerchantRegisterResponse> {
        return ApiResponse(
            success = true,
            data = service.register(request.data)
        )
    }

}