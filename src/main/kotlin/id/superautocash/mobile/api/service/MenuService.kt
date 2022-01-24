package id.superautocash.mobile.api.service

import id.superautocash.mobile.api.controller.request.CreateMenuRequest
import id.superautocash.mobile.api.controller.response.CreateMenuResponse
import id.superautocash.mobile.api.controller.response.GetAllMenuResponse

interface MenuService {

    fun getAll(page: Int?, size: Int?, merchantId: Int): GetAllMenuResponse

    fun create(request: CreateMenuRequest?): CreateMenuResponse

}