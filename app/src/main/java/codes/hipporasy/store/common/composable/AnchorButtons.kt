package codes.hipporasy.store.common.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AnchorButtons(
    modifier: Modifier = Modifier,
    scrollBehavior: AnchoredButtonScrollingBehavior?
) {

    @Composable
    fun containerColor(colorTransitionFraction: Float): Color {
        return lerp(
            MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
            MaterialTheme.colorScheme.background,
            FastOutLinearInEasing.transform(colorTransitionFraction)
        )
    }

    val localDensity = LocalDensity.current
    val colorTransitionFraction = scrollBehavior?.state?.overlappedFraction ?: 0f
    val fraction = if (colorTransitionFraction > 0.01f) 1f else 0f
    val containerColor by animateColorAsState(
        targetValue = containerColor(fraction),
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
    )

    // Set up support for resizing the top app bar when vertically dragging the bar itself.
    val appBarDragModifier = if (scrollBehavior != null) {
        Modifier.draggable(
            orientation = Orientation.Vertical,
            state = rememberDraggableState { delta ->
//                scrollBehavior.state.heightOffset = scrollBehavior.state.heightOffset + delta
            },
            onDragStopped = { velocity ->
//                settleAppBar(
//                    scrollBehavior.state,
//                    velocity,
//                )
            }
        )
    } else {
        Modifier
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .then(appBarDragModifier),
        color = containerColor
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp
                )
                .onGloballyPositioned { coordinates ->
                    val heightOffsetLimit = with(localDensity) {
                        -coordinates.size.height
                            .toDp()
                            .toPx()
                    }
                    if (scrollBehavior?.state?.heightOffsetLimit != heightOffsetLimit) {
                        scrollBehavior?.state?.heightOffsetLimit = heightOffsetLimit
                    }
                }
        ) {
            Button(
                modifier = modifier,
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "title",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "Favorite")

            }
            TextButton(
                onClick = { },
                contentPadding = ButtonDefaults.TextButtonContentPadding
            ) {
                Text(text = "Text Button")
            }
        }
    }
}
