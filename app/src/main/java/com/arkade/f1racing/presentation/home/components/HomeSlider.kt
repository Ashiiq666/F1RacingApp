package com.arkade.f1racing.presentation.home.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import com.arkade.f1racing.R
import com.arkade.f1racing.ui.theme.extraBoldTextStyle
import com.arkade.f1racing.ui.theme.mediumTextStyle
import com.arkade.f1racing.ui.theme.montserratFont
import kotlinx.coroutines.delay

@Composable
fun HomeSlider(
    modifier: Modifier = Modifier
) {
    val sliderItems = listOf(
        HomeSliderItem(
            imageRes = R.drawable.diamond,
            title = "Formula 1\nRacing",
            subtitle = "Stay updated with real-time race status,\nupdates, and more"
        ),
        HomeSliderItem(
            imageRes = R.drawable.diamond,
            title = "Discover new\ncircuits",
            subtitle = "Find amazing tracks and hidden gems\naround the world"
        ),
        HomeSliderItem(
            imageRes = R.drawable.diamond,
            title = "Race with\nconfidence",
            subtitle = "Get the best insights and reliable\nracing experience"
        )
    )

    val pagerState = rememberPagerState()

    // Auto sliding effect
    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000) // 3 seconds delay
            val nextPage = (pagerState.currentPage + 1) % sliderItems.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(0.dp), // No rounded corners for full width
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Box {
            HorizontalPager(
                count = sliderItems.size,
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                HomeSliderCard(item = sliderItems[page])
            }

            RectPagerIndicator(
                count = sliderItems.size,
                current = pagerState.currentPage,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            )
        }
    }
}

@Composable
private fun HomeSliderCard(
    item: HomeSliderItem
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp),
            painter = painterResource(item.imageRes),
            contentDescription = item.title,
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.2f),
                            Color.Transparent,
                            Color.Transparent
                        ),
                        startY = 0f,
                        endY = 1200f
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 26.dp, top = 53.dp, end = 24.dp)
                .align(Alignment.TopStart)
        ) {
            Text(
                text = item.title,
                style = extraBoldTextStyle.copy(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = montserratFont
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = item.subtitle,
                style = mediumTextStyle.copy(
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    lineHeight = 22.sp,
                    fontFamily = montserratFont
                )
            )
        }
    }
}

@Composable
fun RectPagerIndicator(
    count: Int,
    current: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(count) { index ->
            val selected = current == index
            val width by animateDpAsState(
                targetValue = if (selected) 24.dp else 4.dp,
                label = "indicator_width"
            )
            val color = if (selected) Color.White else Color(0xFFE1E1E1)

            Box(
                Modifier
                    .width(width)
                    .height(4.dp)
                    .clip(RoundedCornerShape(50))
                    .background(color)
            )
        }
    }
}

