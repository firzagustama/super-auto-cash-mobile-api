package id.superautocash.mobile.api.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.superautocash.mobile.api.controller.request.ApiRequest
import id.superautocash.mobile.api.controller.request.BaseRequest
import id.superautocash.mobile.api.controller.response.ApiResponse
import id.superautocash.mobile.api.controller.response.BaseResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import kotlin.reflect.KClass

@SpringBootTest
@AutoConfigureMockMvc
class BaseApiTests {
    @Autowired
    lateinit var mockMvc: MockMvc

    fun <T: BaseResponse> post(url: String, request: BaseRequest, responseClass: KClass<T>): ApiResponse<T> {
        val responseJson = mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .content(ApiRequest(data = request).toJson())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andReturn().response.contentAsString
        return Gson().fromJson(responseJson, TypeToken.getParameterized(ApiResponse::class.java, responseClass.java).type)
    }
}