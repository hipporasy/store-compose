package codes.hipporasy.store.presentation.posts

import codes.hipporasy.store.data.model.Post

data class PostState(
    val posts: List<Post>? = null
)

sealed class PostEvent {
    object Load : PostEvent()
}