package com.arkade.f1racing.utils

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Material 3 Circular Loading Indicator (Default)
 */
@Composable
fun M3CircularLoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFFF5A08),
    trackColor: Color = Color.White.copy(alpha = 0.2f),
    size: Dp = 48.dp,
    strokeWidth: Dp = 4.dp
) {
    CircularProgressIndicator(
        modifier = modifier.size(size),
        color = color,
        strokeWidth = strokeWidth,
        trackColor = trackColor,
        strokeCap = StrokeCap.Round
    )
}

/**
 * Material 3 Expressive Circular Loading Indicator with Scale Animation
 */
@Composable
fun M3ExpressiveCircularLoader(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFFF5A08),
    trackColor: Color = Color.White.copy(alpha = 0.2f),
    size: Dp = 48.dp,
    strokeWidth: Dp = 4.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "expressive_loader")

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    CircularProgressIndicator(
        modifier = modifier
            .size(size)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        color = color,
        strokeWidth = strokeWidth,
        trackColor = trackColor,
        strokeCap = StrokeCap.Round
    )
}

/**
 * Material 3 Circular Determinate Loading Indicator
 */
@Composable
fun M3DeterminateCircularLoader(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFFF5A08),
    trackColor: Color = Color.White.copy(alpha = 0.2f),
    size: Dp = 48.dp,
    strokeWidth: Dp = 4.dp
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),
        label = "progress"
    )

    CircularProgressIndicator(
        progress = { animatedProgress },
        modifier = modifier.size(size),
        color = color,
        strokeWidth = strokeWidth,
        trackColor = trackColor,
        strokeCap = StrokeCap.Round
    )
}

/**
 * Material 3 Dots Loading Indicator
 */
@Composable
fun M3DotsLoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFFF5A08),
    dotSize: Dp = 12.dp,
    dotSpacing: Dp = 8.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "dots")

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dotSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { index ->
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.5f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(600, easing = FastOutSlowInEasing, delayMillis = index * 100),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "dot_$index"
            )

            Box(
                modifier = Modifier
                    .size(dotSize)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .background(color, CircleShape)
            )
        }
    }
}

/**
 * Material 3 Linear Loading Indicator
 */
@Composable
fun M3LinearLoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFFF5A08),
    trackColor: Color = Color.White.copy(alpha = 0.2f),
    strokeWidth: Dp = 4.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "linear")

    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "progress"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(strokeWidth)
    ) {
        // Track
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(strokeWidth)
                .background(trackColor, RoundedCornerShape(strokeWidth / 2))
        )

        // Progress
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(strokeWidth)
                .background(color, RoundedCornerShape(strokeWidth / 2))
        )
    }
}

/**
 * Material 3 Pulsing Circle Loader
 */
@Composable
fun M3PulsingCircleLoader(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFFF5A08),
    size: Dp = 48.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Box(
        modifier = modifier
            .size(size)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .background(color, CircleShape)
    )
}

/**
 * Material 3 Orbit Loader
 */
@Composable
fun M3OrbitLoader(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFFF5A08),
    size: Dp = 48.dp,
    dotSize: Dp = 8.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "orbit")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Canvas(modifier = modifier.size(size)) {
        val radius = size.toPx() / 2 - dotSize.toPx()
        val centerX = size.toPx() / 2
        val centerY = size.toPx() / 2

        repeat(8) { index ->
            val angle = (rotation + index * 45) * PI / 180
            val x = centerX + radius * cos(angle).toFloat()
            val y = centerY + radius * sin(angle).toFloat()
            val alpha = 1f - (index / 8f) * 0.7f

            drawCircle(
                color = color.copy(alpha = alpha),
                radius = dotSize.toPx() / 2,
                center = Offset(x, y)
            )
        }
    }
}