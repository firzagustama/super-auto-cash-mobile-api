package id.superautocash.mobile.api.controller.response

import id.superautocash.mobile.api.controller.model.Menu

data class GetMenuResponse(
    val menu: Menu
): BaseResponse()