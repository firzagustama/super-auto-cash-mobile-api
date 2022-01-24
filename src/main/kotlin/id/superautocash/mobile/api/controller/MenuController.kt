package id.superautocash.mobile.api.controller

import id.superautocash.mobile.api.controller.request.ApiRequest
import id.superautocash.mobile.api.controller.request.CreateMenuRequest
import id.superautocash.mobile.api.controller.request.UpdateMenuRequest
import id.superautocash.mobile.api.controller.response.ApiResponse
import id.superautocash.mobile.api.controller.response.CreateMenuResponse
import id.superautocash.mobile.api.controller.response.GetAllMenuResponse
import id.superautocash.mobile.api.controller.response.GetMenuResponse
import id.superautocash.mobile.api.service.MenuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["menu"])
class MenuController @Autowired constructor(
    val service: MenuService
) {

    @GetMapping(
        value = ["/detail/{id}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun get(@PathVariable(name = "id") menuId: Int): ApiResponse<GetMenuResponse> {
        return ApiResponse(
            success = true,
            data = service.get(menuId)
        )
    }

    @GetMapping(
        value = ["/{id}"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun getAll(@RequestParam page: Int?, @RequestParam size: Int?, @PathVariable(name = "id") merchantId: Int): ApiResponse<GetAllMenuResponse> {
        return ApiResponse(
            success = true,
            data = service.getAll(page, size, merchantId)
        )
    }

    @PostMapping(
        value = ["/create"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun create(@RequestBody request: ApiRequest<CreateMenuRequest>): ApiResponse<CreateMenuResponse> {
        return ApiResponse(
            success = true,
            data = service.create(request.data)
        )
    }

    @PostMapping(
        value = ["/update"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun update(@RequestBody request: ApiRequest<UpdateMenuRequest>): ApiResponse<GetMenuResponse> {
        return ApiResponse(
            success = true,
            data = service.update(request.data)
        )
    }
}