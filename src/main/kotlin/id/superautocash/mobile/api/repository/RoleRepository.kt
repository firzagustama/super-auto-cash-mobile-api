package id.superautocash.mobile.api.repository

import id.superautocash.mobile.api.entity.Role
import org.springframework.data.repository.CrudRepository

interface RoleRepository: CrudRepository<Role, Int> {
}