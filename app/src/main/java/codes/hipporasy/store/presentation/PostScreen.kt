package codes.hipporasy.store.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import codes.hipporasy.store.data.model.Post
import codes.hipporasy.store.presentation.base.BaseViewState
import codes.hipporasy.store.presentation.posts.PostEvent
import codes.hipporasy.store.presentation.posts.PostState
import codes.hipporasy.store.presentation.posts.PostViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun PostScreen(
    viewModel: PostViewModel = hiltViewModel(),
    navigator: DestinationsNavigator

) {

    val state by viewModel.uiState.collectAsState()

    PostDetailBody({ navigator.navigateUp() }) {
        when (state) {
            is BaseViewState.Data<*> -> PostList(state = state.cast<BaseViewState.Data<PostState>>().value)
            is BaseViewState.Loading -> Text(text = "Loading")
            else -> Text("Nothing")
        }

    }

    LaunchedEffect(key1 = viewModel, block = {
        viewModel.onTriggerEvent(PostEvent.Load)
    })

}

@Composable
private fun PostDetailBody(
    pressOnBack: () -> Unit = {},
    pageContent: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Post",
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                navigationIcon = {
                    Icon(
                        rememberVectorPainter(Icons.Filled.ArrowBack),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { pressOnBack.invoke() }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
        content = { pageContent.invoke(it) }
    )
}

@Composable
fun PostList(state: PostState) {
    Column() {
        for (post in state.posts ?: listOf()) {
            PostCard(post)
        }
    }
}

@Composable
fun PostCard(post: Post) {
    Card(elevation = 4.dp) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = post.title)
            Text(text = post.body)
        }
    }
}

val Any.classTag: String get() = this.javaClass.canonicalName.orEmpty()

val Any.methodTag get() = classTag + object : Any() {}.javaClass.enclosingMethod?.name

fun Any.hashCodeAsString(): String {
    return hashCode().toString()
}

inline fun <reified T : Any> Any.cast(): T {
    return this as T
}

