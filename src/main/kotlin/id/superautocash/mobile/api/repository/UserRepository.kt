package id.superautocash.mobile.api.repository

import id.superautocash.mobile.api.entity.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int> {

    fun findByUsername(username: String): User?

}