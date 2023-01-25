package codes.hipporasy.store.data.repository

import codes.hipporasy.store.data.model.Post


interface PostRepository {

    suspend fun getPosts(): List<Post>

}