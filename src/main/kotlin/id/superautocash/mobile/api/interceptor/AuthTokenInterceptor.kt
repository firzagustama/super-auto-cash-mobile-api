package id.superautocash.mobile.api.interceptor

import id.superautocash.mobile.api.security.jwt.JwtUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthTokenInterceptor: OncePerRequestFilter(){
    @Autowired
    lateinit var jwtUtils: JwtUtils

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            parseJwtToken(request)?.let {
                if (!jwtUtils.validateToken(it, request)) return@let
                val user = jwtUtils.getUserFromJwtToken(it)
                val auth = UsernamePasswordAuthenticationToken(user, null, user.authorities).apply {
                    this.details = WebAuthenticationDetailsSource().buildDetails(request)
                }
                SecurityContextHolder.getContext().authentication = auth
            }
        } catch (e: Exception) {
            logger.error("Cannot set user authentication: {}", e)
        }
        filterChain.doFilter(request, response)
    }

    private fun parseJwtToken(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader("Authorization")
        if (!headerAuth.isNullOrBlank() && headerAuth.startsWith("Bearer")) {
            return headerAuth.substring(7, headerAuth.length)
        }
        return null
    }
}