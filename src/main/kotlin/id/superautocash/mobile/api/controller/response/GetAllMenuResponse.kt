package id.superautocash.mobile.api.controller.response

import id.superautocash.mobile.api.controller.model.Menu
import id.superautocash.mobile.api.controller.model.PaginationInfo

data class GetAllMenuResponse(
    val info: PaginationInfo,
    val menus: List<Menu>
): BaseResponse()
