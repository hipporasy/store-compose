package codes.hipporasy.store.presentation

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true) // sets this as the start destination of the default nav graph
@Destination
@Composable
fun HomeScreen(
    // navigator: DestinationsNavigator
) {
    /*...*/
//    navigator.navigate(ProfileScreenDestination(id = 7, groupName = "Kotlin programmers"))

}

@Destination
@Composable
fun ProfileScreen(
    id: Int, // <-- required navigation argument
    groupName: String?, // <-- optional navigation argument
    isOwnUser: Boolean = false // <-- optional navigation argument
) { /*...*/ }