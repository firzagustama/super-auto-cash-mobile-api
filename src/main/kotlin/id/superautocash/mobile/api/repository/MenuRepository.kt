package id.superautocash.mobile.api.repository

import id.superautocash.mobile.api.entity.Menu
import org.springframework.data.repository.CrudRepository

interface MenuRepository: CrudRepository<Menu, Int> {
}