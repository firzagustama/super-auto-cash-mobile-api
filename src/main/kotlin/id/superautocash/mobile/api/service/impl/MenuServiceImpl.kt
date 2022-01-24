package id.superautocash.mobile.api.service.impl

import id.superautocash.mobile.api.controller.model.PaginationInfo
import id.superautocash.mobile.api.controller.response.GetAllMenuResponse
import id.superautocash.mobile.api.repository.MenuRepository
import id.superautocash.mobile.api.service.MenuService
import id.superautocash.mobile.api.utils.paramNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
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
}