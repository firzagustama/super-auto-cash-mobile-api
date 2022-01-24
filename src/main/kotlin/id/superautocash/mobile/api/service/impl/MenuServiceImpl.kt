package id.superautocash.mobile.api.service.impl

import id.superautocash.mobile.api.controller.model.PaginationInfo
import id.superautocash.mobile.api.controller.request.CreateMenuRequest
import id.superautocash.mobile.api.controller.response.CreateMenuResponse
import id.superautocash.mobile.api.controller.response.GetAllMenuResponse
import id.superautocash.mobile.api.repository.MenuRepository
import id.superautocash.mobile.api.security.entity.UserDetailsSecurity
import id.superautocash.mobile.api.service.MenuService
import id.superautocash.mobile.api.utils.paramIsTrue
import id.superautocash.mobile.api.utils.paramNotNull
import id.superautocash.mobile.api.utils.paramNotNullOrBlank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class MenuServiceImpl @Autowired constructor(
    val repository: MenuRepository
): MenuService {

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
        }
        // get logged in merchant id
        val auth = SecurityContextHolder.getContext().authentication
        val user = (auth.principal as UserDetailsSecurity).user
        // insert data
        val menu = repository.save(request.toEntity(user.id!!))
        // assemble response
        return CreateMenuResponse(menu.id!!)
    }
}