package codes.hipporasy.store.domain.repository

import codes.hipporasy.store.data.model.Post
import codes.hipporasy.store.data.repository.PostRepository
import codes.hipporasy.store.data.service.DummyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor(private val _api: DummyApi) : PostRepository {

    override suspend fun getPosts(): List<Post> {
        return _api.getPosts().posts
    }

}