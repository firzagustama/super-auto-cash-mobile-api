package id.superautocash.mobile.api.controller

import id.superautocash.mobile.api.controller.request.ApiRequest
import id.superautocash.mobile.api.controller.response.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["auth"])
class AuthController {

    @PostMapping(
        value = ["/check"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun check(@RequestBody request: ApiRequest<Nothing>): ApiResponse<Nothing> {
        return ApiResponse(
            success = true,
            data = null
        )
    }

}