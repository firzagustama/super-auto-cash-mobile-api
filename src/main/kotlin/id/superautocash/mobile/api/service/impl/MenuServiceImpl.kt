package id.superautocash.mobile.api.service.impl

import id.superautocash.mobile.api.controller.model.PaginationInfo
import id.superautocash.mobile.api.controller.request.CreateMenuRequest
import id.superautocash.mobile.api.controller.request.UpdateMenuRequest
import id.superautocash.mobile.api.controller.response.CreateMenuResponse
import id.superautocash.mobile.api.controller.response.GetAllMenuResponse
import id.superautocash.mobile.api.controller.response.GetMenuResponse
import id.superautocash.mobile.api.enums.GeneralExceptionEnum
import id.superautocash.mobile.api.exception.ApiException
import id.superautocash.mobile.api.repository.MenuRepository
import id.superautocash.mobile.api.service.MenuService
import id.superautocash.mobile.api.utils.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.*

@Service
class MenuServiceImpl @Autowired constructor(
    val repository: MenuRepository
): MenuService {

    override fun get(menuId: Int): GetMenuResponse {
        val menu = repository.findById(menuId)
            .orElseThrow { ApiException(GeneralExceptionEnum.MENU_NOT_FOUND) }
            .toMenuModel()
        return GetMenuResponse(menu)
    }

    override fun getAll(page: Int?, size: Int?, merchantId: Int): GetAllMenuResponse {
        // param check
        paramNotNull(merchantId, "merchantId")
        // find all with pageable
        var pageable: Pageable = Pageable.unpaged()
        val info = PaginationInfo(repository.count())
        if (page != null && size != null) {
            pageable = PageRequest.of(page, size)

            val pageUrl = "/menu/%d?page=%d&size=%d"
            val currentCount = (page + 1) * size
            info.pages = pageUrl.format(merchantId, page, size)
            info.next = if (info.count - currentCount > 0) pageUrl.format(merchantId, page + 1, size) else null
            info.prev = if (page > 0) pageUrl.format(merchantId, page - 1, size) else null
        }
        // return response
        return GetAllMenuResponse(
            info = info,
            menus = repository.findAllByMerchantId(merchantId, pageable).map { it.toMenuModel() }
        )
    }

    override fun create(request: CreateMenuRequest?): CreateMenuResponse {
        // param check
        paramNotNull(request, "request")
        with (request!!) {
            paramNotNullOrBlank(name, "name")
            paramNotNullOrBlank(imageUrl, "imageUrl")
            paramNotNull(price, "price")
            paramIsTrue(price!! >= 0, "price","must be greater or equals than 0")
            paramNotNull(getLoggedInUserId(), "userId")
        }
        // insert data
        val menu = repository.save(request.toEntity(getLoggedInUserId()!!))
        // assemble response
        return CreateMenuResponse(menu.id!!)
    }

    override fun update(request: UpdateMenuRequest?): GetMenuResponse {
        // param check
        paramNotNull(request, "request")
        with(request!!) {
            paramNotNull(id, "id")
            paramNotNullOrBlank(name, "name")
            paramNotNullOrBlank(imageUrl, "imageUrl")
            paramNotNull(price, "price")
            paramIsTrue(price!! >= 0, "price", "must be greater or equals than 0")
        }
        // find menu in repository
        val menu = repository.findById(request.id!!)
            .orElseThrow { throw ApiException(GeneralExceptionEnum.MENU_NOT_FOUND) }
        // check if update merchantId is logged in merchantId
        if (menu.merchantId != getLoggedInUserId()) throwException(GeneralExceptionEnum.FORBIDDEN)
        // update menu
        with(menu) {
            name = request.name!!
            imageUrl = request.imageUrl!!
            price = request.price!!
            description = request.description!!
            updatedDate = Timestamp(Date().time)
        }
        repository.save(menu)
        // assemble response
        return GetMenuResponse(
            menu = menu.toMenuModel()
        )
    }
}