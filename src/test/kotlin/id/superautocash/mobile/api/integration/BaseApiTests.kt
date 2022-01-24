package id.superautocash.mobile.api.integration

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.superautocash.mobile.api.ApplicationTests
import id.superautocash.mobile.api.controller.request.ApiRequest
import id.superautocash.mobile.api.controller.request.BaseRequest
import id.superautocash.mobile.api.controller.response.ApiResponse
import id.superautocash.mobile.api.controller.response.BaseResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import kotlin.reflect.KClass

class BaseApiTests: ApplicationTests() {
    @Autowired
    lateinit var mockMvc: MockMvc

    private val headers = HttpHeaders()

    init {
        clearHeader()
    }

    fun addHeader(headerName: String, headerValue: String) {
        headers.add(headerName, headerValue)
    }

    final fun clearHeader() {
        headers.clear()
        headers.add("Content-Type", "application/json")
        headers.add("Accept", "application/json")
    }

    fun <T: BaseResponse> get(url: String, responseClass: KClass<T>): ApiResponse<T> {
        val responseJson = mockMvc.perform(MockMvcRequestBuilders
            .get(url)
            .headers(this.headers)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andReturn().response.contentAsString
        return Gson().fromJson(responseJson, TypeToken.getParameterized(ApiResponse::class.java, responseClass.java).type)
    }

    fun <T: BaseResponse> post(url: String, request: BaseRequest, responseClass: KClass<T>): ApiResponse<T> {
        val responseJson = mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .headers(this.headers)
            .content(ApiRequest(data = request).toJson())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andReturn().response.contentAsString
        return Gson().fromJson(responseJson, TypeToken.getParameterized(ApiResponse::class.java, responseClass.java).type)
    }
}