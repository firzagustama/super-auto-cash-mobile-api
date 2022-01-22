package id.superautocash.mobile.api.security.service

import id.superautocash.mobile.api.entity.User
import id.superautocash.mobile.api.enums.GeneralExceptionEnum
import id.superautocash.mobile.api.exception.ApiException
import id.superautocash.mobile.api.repository.UserRepository
import id.superautocash.mobile.api.security.entity.UserDetailsSecurity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailSecurityService: UserDetailsService {

    @Autowired
    lateinit var repository: UserRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        val user: User = repository.findByUsernameOrEmail(username, username) ?: throw ApiException(GeneralExceptionEnum.USER_NOT_FOUND)
        return UserDetailsSecurity(user)
    }
}