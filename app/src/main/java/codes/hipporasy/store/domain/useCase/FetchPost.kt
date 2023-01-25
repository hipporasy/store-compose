package codes.hipporasy.store.domain.useCase

import codes.hipporasy.store.data.model.DataState
import codes.hipporasy.store.data.model.Post
import codes.hipporasy.store.data.repository.PostRepository
import codes.hipporasy.store.data.service.apiCall
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class FetchPost @Inject constructor(
    private val repository: PostRepository
) : BaseUseCase<Unit, List<Post>>() {
    override suspend fun FlowCollector<DataState<List<Post>>>.execute(params: Unit) {
        val service = apiCall { repository.getPosts() }
        emit(service)
    }
}
