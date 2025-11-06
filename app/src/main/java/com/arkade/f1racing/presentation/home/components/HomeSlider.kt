package com.arkade.f1racing.presentation.home.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import com.arkade.f1racing.R
import com.arkade.f1racing.ui.theme.boldTextStyle
import com.arkade.f1racing.ui.theme.montserratFont
import com.arkade.f1racing.ui.theme.space_gro_teskFont
import kotlinx.coroutines.delay

@Composable
fun HomeSlider(
    modifier: Modifier = Modifier
) {
    val sliderItems = listOf(
        HomeSliderItem(
            position = "01",
            wins = "09",
            points = "429",
            driverName = "Lando"
        ),
        HomeSliderItem(
            position = "02",
            wins = "08",
            points = "398",
            driverName = "Max"
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

@Composable
private fun HomeSliderCard(
    item: HomeSliderItem
) {
    val orangeColor = Color(0xFFFF5A08)
    val whiteColor = Color(0xFFFFFFFF)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(480.dp)
            .background(orangeColor)
    ) {
        // Get Pro box and Lando text - aligned TopStart
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 24.dp, top = 50.dp)
        ) {
            // Get Pro box
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color(0xFFFFFFFF).copy(alpha = 0.1f))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.diamond),
                        contentDescription = "Diamond icon",
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Get Pro",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = montserratFont
                    )
                }
            }

            Text(
                text = "Lando",
                style = boldTextStyle.copy(
                    color = Color(0xFFFFF2AF).copy(alpha = 0.3f),
                    fontSize = 164.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = space_gro_teskFont
                ),
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Visible,
                softWrap = false
            )
        }

        // Driver image (drive.png) on the right, aligned to bottom
        Image(
            painter = painterResource(R.drawable.driver),
            contentDescription = "Driver with helmet",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .width(320.dp),
            contentScale = ContentScale.Fit
        )

        // Semi-transparent black overlay with blur effect - gradient from bottom to top
        // This layer sits on top of background/driver but below statistics
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.93f), // Bottom - 90% opacity
                            Color.Black.copy(alpha = 0.8f), // Middle
                            Color.Black.copy(alpha = 0.4f), // Upper middle
                            Color.Black.copy(alpha = 0.1f), // Upper middle
                            Color.Transparent // Top - fully transparent
                        ),
                        startY = Float.POSITIVE_INFINITY, // Bottom
                        endY = 0f // Top
                    )
                )
                .blur(radius = 8.dp)
        )

        // Statistics section on the left - positioned near bottom indicator with 42dp spacing
        // This section is elevated above the overlay layer
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 27.dp,
                    bottom = 66.dp
                ) // 42dp above indicator + 24dp indicator bottom padding
        ) {
            // Position and Wins in a single row
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Position
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_position),
                        contentDescription = "Position icon",
                        modifier = Modifier.size(16.dp)
                    )
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = item.position,
                            color = whiteColor,
                            fontSize = 18.sp,
                            fontFamily = space_gro_teskFont,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = " Pos",
                            color = whiteColor,
                            fontSize = 10.sp,
                            fontFamily = space_gro_teskFont,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Wins
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_win),
                        contentDescription = "Wins icon",
                        modifier = Modifier.size(16.dp)
                    )
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = item.wins,
                            color = whiteColor,
                            fontSize = 18.sp,
                            fontFamily = space_gro_teskFont,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = " Wins",
                            color = whiteColor,
                            fontSize = 10.sp,
                            fontFamily = space_gro_teskFont,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Points number with gradient (big letter size 72dp) and PTS box aligned horizontally
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                // Points number
                Text(
                    text = item.points,
                    style = TextStyle(
                        fontSize = 72.sp,
                        fontFamily = space_gro_teskFont,
                        fontWeight = FontWeight.Light,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                whiteColor, // Bottom - #FFFFFF
                                orangeColor // Top - #FF5A08
                            )
                        )
                    )
                )

                // PTS text badge - aligned horizontally next to the number
                Box(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .wrapContentHeight()
                        .width(37.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color(0xFFFF5A08)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "PTS",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = space_gro_teskFont,
                        fontWeight = FontWeight.Medium,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }

}




