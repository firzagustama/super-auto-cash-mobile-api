package id.superautocash.mobile.api.controller

import id.superautocash.mobile.api.controller.response.ApiResponse
import id.superautocash.mobile.api.controller.response.GetAllMenuResponse
import id.superautocash.mobile.api.service.MenuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["menu"])
class MenuController @Autowired constructor(
    val service: MenuService
) {

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
}