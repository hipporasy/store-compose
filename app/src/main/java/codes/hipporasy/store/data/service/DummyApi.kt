package codes.hipporasy.store.data.service

import codes.hipporasy.store.data.model.DataState
import codes.hipporasy.store.data.model.Post
import codes.hipporasy.store.data.model.PostResponse
import retrofit2.HttpException
import retrofit2.http.GET
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface DummyApi {

    @GET("posts")
    suspend fun getPosts(): PostResponse

    companion object {
        const val BASE_URL = "https://dummyjson.com"
    }

}

suspend fun <T : Any> apiCall(call: suspend () -> T): DataState<T> {
    return try {
        val response = call()
        DataState.Success(response)
    } catch (ex: Throwable) {
        DataState.Error(ex.handleThrowable())
    }
}
