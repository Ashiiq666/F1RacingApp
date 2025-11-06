package com.arkade.f1racing.presentation.navigations

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.arkade.f1racing.R

sealed class BottomNavItem(
    val route: String,
    val selectedIconRes: Int,
    val unselectedIconRes: Int,
    val label: String
) {
    object Home : BottomNavItem(
        route = "home",
        selectedIconRes = R.drawable.ic_home,
        unselectedIconRes = R.drawable.ic_home_inactive,
        label = "Home"
    )

    object Event : BottomNavItem(
        route = "event",
        selectedIconRes = R.drawable.calender_active,
        unselectedIconRes = R.drawable.calender_inactive,
        label = "Event"
    )

    object Result : BottomNavItem(
        route = "result",
        selectedIconRes = R.drawable.trophy_active,
        unselectedIconRes = R.drawable.trophy_inactive,
        label = "Result"
    )

    object Web : BottomNavItem(
        route = "web",
        selectedIconRes = R.drawable.ic_web_active,
        unselectedIconRes = R.drawable.ic_web_inactive,
        label = "Web"
    )

    object Profile : BottomNavItem(
        route = "profile",
        selectedIconRes = R.drawable.ic_profile_active,
        unselectedIconRes = R.drawable.ic_profile_inactive,
        label = "Profile"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    selectedRoute: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Event,
        BottomNavItem.Result,
        BottomNavItem.Web,
        BottomNavItem.Profile
    )

    val selectedIndex = items.indexOfFirst { it.route == selectedRoute }

    // Animate the selector position
    val selectorOffset by animateFloatAsState(
        targetValue = selectedIndex.toFloat(),
        animationSpec = tween(durationMillis = 300),
        label = "selector_offset"
    )

    // Animated width for the pill selector
    val selectorWidth by animateDpAsState(
        targetValue = 80.dp,
        animationSpec = tween(durationMillis = 300),
        label = "selector_width"
    )

    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        NavigationBar(
            modifier = modifier.wrapContentSize(),
            containerColor = Color.Black,
            contentColor = Color.White,
            tonalElevation = 0.dp
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = selectedRoute == item.route

                NavigationBarItem(
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                    icon = {
                        Box(
                            modifier = Modifier.height(48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            // Animated selector background
                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .offset(x = (selectorOffset - index) * 72.dp)
                                        .width(selectorWidth)
                                        .height(48.dp)
                                        .clip(RoundedCornerShape(300.dp))
                                        .background(Color(0xFF212121).copy(alpha = 0.7f))
                                )
                            }

                            // Icon
                            Icon(
                                painter = painterResource(
                                    id = if (isSelected) item.selectedIconRes else item.unselectedIconRes
                                ),
                                contentDescription = item.label,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    },
                    selected = isSelected,
                    onClick = { onItemSelected(item.route) },
                    alwaysShowLabel = false,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.White.copy(alpha = 0.6f),
                        indicatorColor = Color.Transparent, // Remove default indicator
                        selectedTextColor = Color.Transparent,
                        unselectedTextColor = Color.Transparent
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                )
            }
        }
    }
}