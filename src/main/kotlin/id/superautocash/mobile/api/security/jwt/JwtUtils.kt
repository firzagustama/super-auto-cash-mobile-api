package id.superautocash.mobile.api.security.jwt

import com.google.gson.Gson
import id.superautocash.mobile.api.security.entity.UserDetailsSecurity
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtUtils {

    @Value("\${superautocash.auth.jwt.secret}")
    lateinit var secretKey: String

    @Value("\${superautocash.auth.jwt.expiration}")
    lateinit var expiration: String

    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun generateToken(auth: Authentication): String {
        val user = (auth.principal as UserDetailsSecurity)
        return Jwts.builder()
            .setSubject(user.toJson())
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + expiration.toLong()))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()
    }

    fun getUserFromJwtToken(token: String): UserDetailsSecurity {
        val userJson = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
        return Gson().fromJson(userJson, UserDetailsSecurity::class.java)
    }

    fun validateToken(token: String, request: HttpServletRequest): Boolean {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            return true
        } catch (e: ExpiredJwtException) {
            logger.info("Expired JWT Token")
            request.setAttribute("expired", e.message)
        }
        return false
    }
}