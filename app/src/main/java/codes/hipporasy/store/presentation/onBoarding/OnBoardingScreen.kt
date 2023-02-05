package codes.hipporasy.store.presentation.onBoarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import codes.hipporasy.store.R
import codes.hipporasy.store.ui.theme.LightGray
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalFoundationApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun OnBoardingScreen(
    navigator: DestinationsNavigator
) {

    var selectedIndex by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState()
    val items = OnBoardingType.values()

    LaunchedEffect(pagerState) {
        // Collect from the a snapshotFlow reading the currentPage
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedIndex = page
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(52.dp))
        HorizontalPager(
            pageCount = items.size,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) {
            OnBoardingContent(type = items[it])
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.padding(start = 32.dp),
        ) {
            DotsIndicator(
                totalDots = items.size,
                selectedIndex = selectedIndex,
            )
            Spacer(modifier = Modifier.fillMaxWidth())
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Skip",
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onSurface
                )
            }
            Button(onClick = { /*TODO*/ }, shape = CircleShape) {
                Text("Get Started")
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}


@Composable
fun OnBoardingContent(type: OnBoardingType) {
    Column(
    ) {
        Box(
            modifier = Modifier
                .height(96.dp)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        ) {

            Text(
                stringResource(id = type.titleRes),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h6
            )
        }
        Spacer(modifier = Modifier.height(52.dp))
        Image(
            painter = painterResource(id = type.imageRes),
            contentDescription = stringResource(id = R.string.image),
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxHeight()
        )
    }
}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
) {

    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()

    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(6.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.primary)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(LightGray)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

enum class OnBoardingType {
    ONE {
        override val titleRes: Int
            get() = R.string.walk_through_one
        override val imageRes: Int
            get() = R.drawable.walkthrough_one
    },
    TWO {
        override val titleRes: Int
            get() = R.string.walk_through_two
        override val imageRes: Int
            get() = R.drawable.walkthrough_two
    },
    THREE {
        override val titleRes: Int
            get() = R.string.walk_through_three
        override val imageRes: Int
            get() = R.drawable.walkthrough_three
    },
    FOUR {
        override val titleRes: Int
            get() = R.string.walk_through_four
        override val imageRes: Int
            get() = R.drawable.walkthrough_four
    };

    abstract val titleRes: Int
    abstract val imageRes: Int

}