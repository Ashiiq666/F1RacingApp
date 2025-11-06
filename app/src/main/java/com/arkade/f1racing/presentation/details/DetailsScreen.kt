package com.arkade.f1racing.presentation.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
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
import com.arkade.f1racing.utils.KotlinExtension
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsScreen(
    race: Race,
    onBackClick: () -> Unit,
    viewModel: DetailsViewModel,

    ) {
    val systemUiController = rememberSystemUiController()
    val fp1Session = race.sessions.find { it.sessionName == "FP1" }
    val uiState by viewModel.uiState.collectAsState()

    // Set navigation bar color to black
    SideEffect {
        systemUiController.setNavigationBarColor(
            color = Color.Black,
            darkIcons = false // false = white icons
        )
    }

    LaunchedEffect(race) {
        viewModel.setRaceAndCalculateNextSession(race)
    }

    var days by remember { mutableStateOf(0L) }
    var hours by remember { mutableStateOf(0L) }
    var minutes by remember { mutableStateOf(0L) }
    
    LaunchedEffect(uiState.nextSession?.startTime) {
        while (true) {
            uiState.nextSession?.startTime?.let { startTime ->
                try {
                    val targetTime = Instant.ofEpochSecond(startTime)
                        .atZone(ZoneId.systemDefault())
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
                fp1Session?.startTime?.let { startTime ->
                    try {
                        val targetTime = Instant.ofEpochSecond(startTime)
                            .atZone(ZoneId.systemDefault())
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
                    // No session found at all
                    days = 0
                    hours = 0
                    minutes = 0
                }
            }
            delay(60000)
        }
    }

    val dateRange = try {
        val startDate = Instant.ofEpochSecond(race.raceStartTime)
            .atZone(ZoneId.systemDefault())
        val endDate = Instant.ofEpochSecond(race.raceEndTime)
            .atZone(ZoneId.systemDefault())
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
            contentDescription = stringResource(R.string.details_background),
            modifier = Modifier
                .fillMaxWidth()
                .height(682.dp)
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop
        )
        
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(scrollState)
                .padding(top = 20.dp, start = 24.dp, end = 24.dp, bottom = 24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back icon
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onBackClick() },
                    contentScale = ContentScale.Fit
                )
                
                Text(
                    text = stringResource(R.string.upcoming_race),
                    color = whiteColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = montserratFont,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                
                // Spacer to balance the back icon on the left
                Spacer(modifier = Modifier.size(24.dp))
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Round
                    Text(
                        text = stringResource(R.string.round, race.round),
                        color = whiteColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = montserratFont
                    )
                    
                    Text(
                        text = race.raceName,
                        color = whiteColor,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = montserratFont
                    )
                    
                    Text(
                        text = KotlinExtension.formatCircuitId(race.circuitId),
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
                    contentDescription = stringResource(R.string.race_track),
                    modifier = Modifier
                        .size(220.dp)
                        .padding(top = 100.dp),
                    contentScale = ContentScale.Fit
                )
            }


            // Countdown section
            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Text(
                    text = stringResource(
                        R.string.starts_in,
                        uiState.nextSession?.sessionName ?: fp1Session?.sessionName ?: "FP1"
                    ),
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
                            text = stringResource(R.string.days),
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
                            text = stringResource(R.string.hours),
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
                            text = stringResource(R.string.minutes),
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
            

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = stringResource(R.string.circuit, KotlinExtension.formatCircuitId(race.circuitId)),
                    color = whiteColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFont
                )
                

                Text(
                    text = stringResource(
                        R.string.circuit_description,
                        race.raceName,
                        KotlinExtension.formatCircuitId(race.circuitId),
                        race.country ?: "",
                        race.locality ?: ""
                    ),
                    color = whiteColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = montserratFont,
                    lineHeight = 20.sp
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                

                Text(
                    text = stringResource(R.string.circuit_facts),
                    color = whiteColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFont
                )
                

                Text(
                    text = stringResource(R.string.circuit_fact_1),
                    color = whiteColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = montserratFont,
                    lineHeight = 20.sp
                )
                

                Divider(
                    color = Color(0xFF333333),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                

                Text(
                    text = stringResource(R.string.circuit_fact_2),
                    color = whiteColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = montserratFont,
                    lineHeight = 20.sp
                )
            }
        }
    }
}
