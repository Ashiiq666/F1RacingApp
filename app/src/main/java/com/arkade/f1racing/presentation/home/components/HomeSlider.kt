package com.arkade.f1racing.presentation.home.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkade.f1racing.R
import com.arkade.f1racing.ui.theme.boldTextStyle
import com.arkade.f1racing.ui.theme.montserratFont
import com.arkade.f1racing.ui.theme.space_gro_teskFont
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay

@Composable
fun HomeSlider(
    modifier: Modifier = Modifier,
    sliderItems: List<HomeSliderItem> = emptyList()
) {
    if (sliderItems.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(480.dp)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFFFF5A08))
        }
        return
    }

    val pagerState = rememberPagerState()


    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % sliderItems.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
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


            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 24.dp, top = 50.dp)
            ) {
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
    when (item) {
        is HomeSliderItem.DriverInfo -> {
            DriverInfoCard(item)
        }
        is HomeSliderItem.Banner -> {
            BannerCard(item)
        }
    }
}

@Composable
private fun DriverInfoCard(
    item: HomeSliderItem.DriverInfo
) {
    val orangeColor = Color(0xFFFF5A08)
    val whiteColor = Color(0xFFFFFFFF)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(480.dp)
            .background(orangeColor)
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 24.dp, top = 50.dp)
        ) {
            Text(
                text = item.driverName,
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


        Image(
            painter = painterResource(R.drawable.driver),
            contentDescription = "Driver with helmet",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .width(320.dp),
            contentScale = ContentScale.Fit
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.93f),
                            Color.Black.copy(alpha = 0.8f),
                            Color.Black.copy(alpha = 0.4f),
                            Color.Black.copy(alpha = 0.1f),
                            Color.Transparent
                        ),
                        startY = Float.POSITIVE_INFINITY,
                        endY = 0f
                    )
                )
                .blur(radius = 8.dp)
        )


        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 27.dp,
                    bottom = 66.dp
                )
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

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

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = item.points,
                    style = TextStyle(
                        fontSize = 72.sp,
                        fontFamily = space_gro_teskFont,
                        fontWeight = FontWeight.Light,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                whiteColor,
                                orangeColor
                            )
                        )
                    )
                )


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

@Composable
private fun BannerCard(
    item: HomeSliderItem.Banner
) {
    val followButtonColor = Color(0xFF86FF0E)
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(480.dp)
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(item.bannerRes),
            contentDescription = "Banner",
            modifier = Modifier
                .width(271.dp)
                .height(214.dp)
                .align(Alignment.Center)
                .padding(bottom = 30.dp),
            contentScale = ContentScale.Crop
        )
        

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(55.dp))
                    .background(followButtonColor)
                    .padding(horizontal = 48.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Follow Us",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFont
                )
            }
        }
    }
}




