package id.superautocash.mobile.api.service

import id.superautocash.mobile.api.controller.request.CreateMenuRequest
import id.superautocash.mobile.api.controller.request.UpdateMenuRequest
import id.superautocash.mobile.api.controller.response.CreateMenuResponse
import id.superautocash.mobile.api.controller.response.GetAllMenuResponse
import id.superautocash.mobile.api.controller.response.GetMenuResponse

interface MenuService {

    fun get(menuId: Int): GetMenuResponse

    fun getAll(page: Int?, size: Int?, merchantId: Int): GetAllMenuResponse

    fun create(request: CreateMenuRequest?): CreateMenuResponse

    fun update(request: UpdateMenuRequest?): GetMenuResponse

}