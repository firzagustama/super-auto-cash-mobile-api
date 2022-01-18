package id.superautocash.mobile.api.interceptor

import id.superautocash.mobile.api.utils.getRequestData
import id.superautocash.mobile.api.utils.getResponseData
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LoggingInterceptor: OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val cachedRequest = ContentCachingRequestWrapper(request)
        val cachedResponse = ContentCachingResponseWrapper(response)
        filterChain.doFilter(cachedRequest, cachedResponse)
        logger.info("${cachedRequest.requestURI} : ${getRequestData(cachedRequest)}")
        logger.info("${cachedRequest.requestURI} : ${getResponseData(cachedResponse)}")
        cachedResponse.copyBodyToResponse()
    }
}