package id.superautocash.mobile.api.service

import id.superautocash.mobile.api.controller.request.MerchantLoginRequest
import id.superautocash.mobile.api.controller.request.MerchantRegisterRequest
import id.superautocash.mobile.api.controller.response.MerchantLoginResponse
import id.superautocash.mobile.api.controller.response.MerchantRegisterResponse

interface MerchantService {

    fun login(request: MerchantLoginRequest?): MerchantLoginResponse

    fun register(request: MerchantRegisterRequest?): MerchantRegisterResponse

}