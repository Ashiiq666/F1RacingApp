package com.arkade.f1racing.presentation.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkade.f1racing.R
import com.arkade.f1racing.data.model.Race
import com.arkade.f1racing.ui.theme.montserratFont
import com.arkade.f1racing.ui.theme.space_gro_teskFont
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsScreen(
    race: Race,
    onBackClick: () -> Unit
) {
    // Find FP1 session
    val fp1Session = race.sessions.find { it.sessionName == "FP1" }
    
    // Calculate countdown
    var days by remember { mutableStateOf(0L) }
    var hours by remember { mutableStateOf(0L) }
    var minutes by remember { mutableStateOf(0L) }
    
    LaunchedEffect(fp1Session?.startTime) {
        while (true) {
            fp1Session?.startTime?.let { startTime ->
                try {
                    val targetTime = ZonedDateTime.parse(startTime, DateTimeFormatter.ISO_DATE_TIME)
                    val now = ZonedDateTime.now(ZoneId.systemDefault())
                    val duration = java.time.Duration.between(now, targetTime)
                    
                    if (duration.isNegative) {
                        days = 0
                        hours = 0
                        minutes = 0
                    } else {
                        days = duration.toDays()
                        val hoursRemaining = duration.minusDays(days)
                        hours = hoursRemaining.toHours()
                        val minutesRemaining = hoursRemaining.minusHours(hours)
                        minutes = minutesRemaining.toMinutes()
                    }
                } catch (e: Exception) {
                    days = 0
                    hours = 0
                    minutes = 0
                }
            } ?: run {
                // No FP1 session found
                days = 0
                hours = 0
                minutes = 0
            }
            delay(60000) // Update every minute
        }
    }
    
    // Format date range
    val dateRange = try {
        val startDate = ZonedDateTime.parse(race.raceStartTime, DateTimeFormatter.ISO_DATE_TIME)
        val endDate = ZonedDateTime.parse(race.raceEndTime, DateTimeFormatter.ISO_DATE_TIME)
        val startDay = startDate.dayOfMonth
        val endDay = endDate.dayOfMonth
        val month = startDate.format(DateTimeFormatter.ofPattern("MMMM"))
        val capitalizedMonth = month.replaceFirstChar { it.uppercaseChar() }
        "$startDay - $endDay $capitalizedMonth"
    } catch (e: Exception) {
        ""
    }
    
    val greenAccent = Color(0xFF009B3A)
    val whiteColor = Color.White
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.details_bg),
            contentDescription = "Details background",
            modifier = Modifier
                .fillMaxWidth()
                .height(682.dp)
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop
        )
        
        // Content overlay - Scrollable layout
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(scrollState)
                .padding(top = 20.dp, start = 24.dp, end = 24.dp, bottom = 24.dp)
        ) {
            // "Upcoming race" header
            Text(
                text = stringResource(R.string.upcoming_race),
                color = whiteColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = montserratFont,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            
            // Race info and track row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left side - Race information
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Round
                    Text(
                        text = "Round ${race.round}",
                        color = whiteColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = montserratFont
                    )
                    
                    // Race name
                    Text(
                        text = race.raceName,
                        color = whiteColor,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = montserratFont
                    )
                    
                    // Locality (green)
                    Text(
                        text = race.locality,
                        color = greenAccent,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = montserratFont
                    )
                    
                    // Date range
                    if (dateRange.isNotEmpty()) {
                        Text(
                            text = dateRange,
                            color = whiteColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = montserratFont
                        )
                    }
                }

                // Right side - Track image
                Image(
                    painter = painterResource(id = R.drawable.track),
                    contentDescription = "Race track",
                    modifier = Modifier
                        .size(230.dp)
                        .padding(top = 100.dp),
                    contentScale = ContentScale.Fit
                )
            }


            // Countdown section
            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Text(
                    text = "FP1 Starts in",
                    color = whiteColor,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium, //todo: chnge
                    fontFamily = montserratFont
                )
                
                // Countdown timer
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    // Days
                    Column(
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = String.format("%02d", days),
                            color = greenAccent,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = montserratFont
                        )
                        Text(
                            text = "Days",
                            color = whiteColor,
                            fontSize = 8.sp,
                            modifier = Modifier.padding(start = 4.dp),
                            fontWeight = FontWeight.Medium,
                            fontFamily = montserratFont //todo: chnge
                        )
                    }
                    
                    // Hours
                    Column(
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = String.format("%02d", hours),
                            color = greenAccent,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = space_gro_teskFont
                        )
                        Text(
                            text = "Hours",
                            color = whiteColor,
                            fontSize = 8.sp,
                            modifier = Modifier.padding(start = 4.dp),
                            fontWeight = FontWeight.Medium,
                            fontFamily = montserratFont
                        )
                    }
                    
                    // Minutes
                    Column(
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = String.format("%02d", minutes),
                            color = greenAccent,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = space_gro_teskFont
                        )
                        Text(
                            text = "Minutes",
                            color = whiteColor,
                            fontSize = 8.sp,
                            modifier = Modifier.padding(start = 4.dp),
                            fontWeight = FontWeight.Medium,
                            fontFamily = montserratFont
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Circuit Information Section
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Circuit Name Header
                Text(
                    text = "${race.circuitName} Circuit",
                    color = whiteColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFont
                )
                
                // Circuit Description
                Text(
                    text = "${race.circuitName} is located in ${race.locality}, ${race.country} and it was designed by German architect Hermann Tilke. It was built on the site of a former camel farm, in ${race.locality}. It measures 5.412 km, has 15 corners and 3 DRS Zones. The Grand Prix have 57 laps. This circuit has 6 alternative layouts.",
                    color = whiteColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = montserratFont,
                    lineHeight = 20.sp
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Circuit Facts Section Header
                Text(
                    text = "Circuit Facts",
                    color = whiteColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFont
                )
                
                // First Fact
                Text(
                    text = "His brother Arthur Leclerc is currently set to race for DAMS in the 2023 F2 Championship",
                    color = whiteColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = montserratFont,
                    lineHeight = 20.sp
                )
                
                // Separator Line
                Divider(
                    color = Color(0xFF333333),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                
                // Second Fact
                Text(
                    text = "He's not related to Édouard Leclerc, the founder of a French supermarket chain",
                    color = whiteColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = montserratFont,
                    lineHeight = 20.sp
                )
            }
        }
    }
}
