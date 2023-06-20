package codes.hipporasy.store.common.composable

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import timber.log.Timber

class AnchoredButtonState(
    var screenHeight: Float,
    initialHeightOffsetLimit: Float,
    initialHeightOffset: Float,
    initialContentOffset: Float,
    initialCurrentOffset: Float,
    initialMaxOffset: Float,
) {

    /**
     * The top app bar's height offset\ limit in pixels, which represents the limit that a top app
     * bar is allowed to collapse to.
     *
     * Use this limit to coerce the [heightOffset] value when it's updated.
     */
    var heightOffsetLimit by mutableStateOf(initialHeightOffsetLimit)
    var currentOffset by mutableStateOf(initialCurrentOffset)
    var maxOffset by mutableStateOf(initialMaxOffset)

    /**
     * The top app bar's current height offset in pixels. This height offset is applied to the fixed
     * height of the app bar to control the displayed height when content is being scrolled.
     *
     * Updates to the [heightOffset] value are coerced between zero and [heightOffsetLimit].
     */
    var heightOffset: Float
        get() = _heightOffset.value
        set(newOffset) {
            _heightOffset.value = newOffset.coerceIn(
                minimumValue = heightOffsetLimit,
                maximumValue = 0f
            )
        }

    /**
     * The total offset of the content scrolled under the top app bar.
     *
     * The content offset is used to compute the [overlappedFraction], which can later be read
     * by an implementation.
     *
     * This value is updated by a [TopAppBarScrollBehavior] whenever a nested scroll connection
     * consumes scroll events. A common implementation would update the value to be the sum of all
     * [NestedScrollConnection.onPostScroll] `consumed.y` values.
     */
    var contentOffset by mutableStateOf(initialContentOffset)

    /**
     * A value that represents the percentage of the app bar area that is overlapping with the
     * content scrolled behind it.
     *
     * A `0.0` indicates that the app bar does not overlap any content, while `1.0` indicates that
     * the entire visible app bar area overlaps the scrolled content.
     */
    val overlappedFraction: Float
        get() {
            Timber.d("$currentOffset $maxOffset")
            if (currentOffset - maxOffset > 0) {
                return 0f
            }
            return 1f
        }

    companion object {
        /**
         * The default [Saver] implementation for [AnchoredButtonState].
         */
        val Saver: Saver<AnchoredButtonState, *> = listSaver(
            save = { listOf(it.heightOffsetLimit, it.heightOffset, it.contentOffset, it.currentOffset, it.maxOffset) },
            restore = {
                AnchoredButtonState(
                    initialHeightOffsetLimit = it[0],
                    initialHeightOffset = it[1],
                    initialContentOffset = it[2],
                    screenHeight = 0f,
                    initialCurrentOffset = it[3],
                    initialMaxOffset = it[4],
                )
            }
        )
    }

    private var _heightOffset = mutableStateOf(initialHeightOffset)
}
@Composable
fun rememberAnchorButtonState(
    initialHeightOffsetLimit: Float = -Float.MAX_VALUE,
    initialHeightOffset: Float = 0f,
    initialContentOffset: Float = 0f,
): AnchoredButtonState {

    return rememberSaveable(saver = AnchoredButtonState.Saver) {
        AnchoredButtonState(
            0f,
            initialHeightOffsetLimit,
            initialHeightOffset,
            initialContentOffset,
            0f,
            0f
        )
    }
}

class AnchoredButtonScrollingBehavior(
    val state: AnchoredButtonState,
    val scrollState: ScrollState?,
    val lazyListState: LazyListState?,
    canScroll: () -> Boolean = { true }
) {

    init {
        state.maxOffset = (lazyListState?.layoutInfo?.viewportEndOffset ?: 0).toFloat()
        state.currentOffset = lazyListState?.layoutInfo?.visibleItemsInfo?.lastOrNull()?.offset?.toFloat() ?: 0f
    }

    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            if (!canScroll()) return Offset.Zero

            state.maxOffset = (lazyListState?.layoutInfo?.viewportEndOffset ?: 0).toFloat()
            state.currentOffset = lazyListState?.layoutInfo?.visibleItemsInfo?.last()?.offset?.toFloat() ?: 0f
            if (consumed.y == 0f && available.y > 0f) {
                // Reset the total content offset to zero when scrolling all the way down.
                // This will eliminate some float precision inaccuracies.
                state.contentOffset = 0f
            } else {
                state.contentOffset += consumed.y
            }
            return Offset.Zero
        }
    }
}
