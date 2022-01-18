package id.superautocash.mobile.api.utils

import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import org.springframework.web.util.WebUtils
import java.nio.charset.Charset
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

fun getRequestData(request: HttpServletRequest): String {
    val wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper::class.java)
    val buf = wrapper?.contentAsByteArray ?: byteArrayOf()
    return String(buf, 0, buf.size, Charset.forName(wrapper?.characterEncoding))
}

fun getResponseData(response: HttpServletResponse): String {
    val wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper::class.java)
    val buf = wrapper?.contentAsByteArray ?: byteArrayOf()
    return String(buf, 0, buf.size, Charset.forName(wrapper?.characterEncoding))
}