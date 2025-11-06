package com.arkade.f1racing.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkade.f1racing.R
import com.arkade.f1racing.presentation.home.components.HomeSlider
import com.arkade.f1racing.ui.theme.montserratFont
import com.arkade.f1racing.ui.theme.space_gro_teskFont
import androidx.compose.ui.text.withStyle
import com.arkade.f1racing.ui.theme.boldTextStyle
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onRaceCardClick: () -> Unit
) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false // false = white icons
        )
        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = false
        )
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(scrollState)
    ) {

        HomeSlider(
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Cards Grid Layout
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
        ) {
            // Top row: Card 1 (left) and Card 2 (right)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Card 1: Dark green background with date/time
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(132.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF044331))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                    ) {
                        // Route icon at top left
                        Image(
                            painter = painterResource(R.drawable.ic_circuit),
                            contentDescription = "Route icon",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(48.dp)
                        )

                        Column(modifier = Modifier.padding(horizontal = 5.dp)) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "FP1",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = space_gro_teskFont
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.calendar_check),
                                        contentDescription = "Calendar icon",
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "04 Friday",
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = space_gro_teskFont
                                    )
                                }


                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = boldTextStyle.copy(
                                                fontSize = 36.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = space_gro_teskFont,
                                                color = Color(0xFF02BB81)
                                            ).toSpanStyle()
                                        ) {
                                            append("8.00")
                                        }
                                        withStyle(
                                            style = boldTextStyle.copy(
                                                fontSize = 20.sp,
                                                fontFamily = space_gro_teskFont,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold
                                            ).toSpanStyle()
                                        ) {
                                            append("AM")
                                        }
                                    },
                                )

                            }




                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {

                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Card 2: Black background with red accent on left and border
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Black)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .border(1.dp, Color(0xFF212121), RoundedCornerShape(16.dp))
                        ) {
                            // Red accent section on the left
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.5f) // Adjust this fraction for the red part's width
                                    .background(
                                        Color(0xFFF51A1E),
                                        RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                                    )
                            )
                            // Content Row
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.ic_route),
                                    contentDescription = "Infinity icon",
                                    modifier = Modifier.size(28.dp)
                                )
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = boldTextStyle.copy(
                                                fontSize = 28.sp,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = space_gro_teskFont,
                                                color = Color.White
                                            ).toSpanStyle()
                                        ) {
                                            append("7015.3")
                                        }
                                        withStyle(
                                            style = boldTextStyle.copy(
                                                fontSize = 14.sp,
                                                fontFamily = space_gro_teskFont,
                                                color = Color.White
                                            ).toSpanStyle()
                                        ) {
                                            append("km")
                                        }
                                    },
                                )
                            }
                        }
                    }

                    // Card 3: Blue background with Formula 1 Education
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF3020FD))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(  8.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Two circle icons
                                    Image(
                                        painter = painterResource(R.drawable.ic_formula),
                                        contentDescription = "Calendar icon",
                                        modifier = Modifier.size(32.dp)
                                    )

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column(
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "Formula 1",
                                            style = boldTextStyle.copy(
                                                fontSize = 12.sp, // Adjusted size
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = montserratFont,
                                                color = Color.White
                                            )
                                        )
                                        Text(
                                            text = "Education",
                                            style = boldTextStyle.copy(
                                                fontSize = 16.sp,
                                                fontFamily = montserratFont,
                                                color = Color.White
                                            )
                                        )
                                    }
                                }

                                Image(
                                    painter = painterResource(R.drawable.ic_redirect),
                                    contentDescription = "Arrow icon",
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(16.dp),

                                    )

                            }
                        }
                    }
                }
            }



            Spacer(modifier = Modifier.height(12.dp))

            // Bottom: Card 4 - Image card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black)
            ) {
                Box {
                    Image(
                        painter = painterResource(R.drawable.instagram),
                        contentDescription = "Instagram image",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}