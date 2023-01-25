package codes.hipporasy.store.presentation.posts

import codes.hipporasy.store.domain.useCase.FetchPost
import codes.hipporasy.store.presentation.base.BaseViewModel
import codes.hipporasy.store.presentation.base.BaseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val fetchPost: FetchPost
) : BaseViewModel<BaseViewState<PostState>, PostEvent>() {

    override fun onTriggerEvent(eventType: PostEvent) {
        when (eventType) {
            is PostEvent.Load -> onLoad()
        }
    }

    private fun onLoad() = safeLaunch {
        execute(fetchPost(Unit)) { dto ->
            setState(BaseViewState.Data(PostState(dto)))
        }
    }

}