package id.superautocash.mobile.api.repository

import id.superautocash.mobile.api.entity.Menu
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface MenuRepository: PagingAndSortingRepository<Menu, Int> {

    fun findAllByMerchantId(merchantId: Int, pageable: Pageable): List<Menu>

}