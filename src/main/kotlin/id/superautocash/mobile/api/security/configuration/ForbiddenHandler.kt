package id.superautocash.mobile.api.security.configuration

import com.google.gson.Gson
import id.superautocash.mobile.api.controller.response.ApiResponse
import id.superautocash.mobile.api.enums.GeneralExceptionEnum
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class ForbiddenHandler: AccessDeniedHandler {

    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        val e = GeneralExceptionEnum.FORBIDDEN
        val requestBody = BufferedReader(InputStreamReader(request!!.inputStream, StandardCharsets.UTF_8))
            .lines()
            .collect(Collectors.joining("\n"))
        val responseBody = Gson().toJson(
            ApiResponse(
            success = false,
            errorCode = e.errorCode,
            errorMessage = e.errorMessage,
            data = null
        )
        )

        logger.info("${request.requestURI} : $requestBody")
        logger.info("${request.requestURI} : $responseBody")

        with(response!!) {
            contentType = "application/json"
            characterEncoding = "UTF-8"
            with(writer) {
                print(responseBody)
                flush()
            }
        }
    }
}