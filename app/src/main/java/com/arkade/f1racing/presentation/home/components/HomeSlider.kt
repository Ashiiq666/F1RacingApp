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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import com.arkade.f1racing.R
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
        // Background text "Lando" (semi-transparent, behind driver)
        Text(
            text = "Lando",
            modifier = Modifier.padding(start = 27.dp, top = 18.dp),
            color = Color(0xFFFFF2AF).copy(
                alpha = 0.3f
            ),
            fontSize = 164.sp,
            maxLines = 1
        )
        
        // Driver image (drive.png) on the right
        Image(
            painter = painterResource(R.drawable.driver),
            contentDescription = "Driver with helmet",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .width(300.dp),
            contentScale = ContentScale.Fit
        )
        
        // Statistics section on the left
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 26.dp, top = 53.dp)
        ) {
            // Position row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_position),
                    contentDescription = "Position icon",
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "${item.position} Pos",
                    color = whiteColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Wins row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_win),
                    contentDescription = "Wins icon",
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "${item.wins} Wins",
                    color = whiteColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Points number with gradient
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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
                
                // PTS badge
                Box(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .background(
                            color = orangeColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "PTS",
                        color = whiteColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }
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

