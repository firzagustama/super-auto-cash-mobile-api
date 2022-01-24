package id.superautocash.mobile.api.utils

import id.superautocash.mobile.api.security.entity.UserDetailsSecurity
import org.springframework.security.core.context.SecurityContextHolder

fun getLoggedInUserId(): Int? {
    val auth = SecurityContextHolder.getContext().authentication
    val user = (auth.principal as UserDetailsSecurity).user
    return user.id
}