package com.example.pyco.views.streak

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pyco.R

@Composable
fun StreakProgress() {
    Box(
        contentAlignment = Alignment.Center, // Center the content within the Box
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        HalfCircle(progress = .75f)
        WoodComponent(.25f, -20f, offsetTop = 80f)
        WoodComponent(.25f, 210f, offsetTop = 80f)
        Flame(.15f)
    }

}

@Composable
fun Flame(size: Float) {
    FlameComponent(
        color = Color(255, 150, 0),
        size = size / 1.0f,
        delay = 0,
        rotation = 2f
    )
    FlameComponent(
        color = Color(255, 150, 0),
        size = size / 1f,
        delay = 10,
        rotation = 2f,
        initialRotation = -25f,
        offsetLeft = 50f,
        offsetTop = -20f
    )
    FlameComponent(
        color = Color(255, 199, 0),
        size = size / 2.0f,
        delay = 0,
        rotation = 2f,
        offsetTop = 20f
    )
}

@Composable
fun FlameComponent(
    color: Color,
    size: Float,
    delay: Int = 0,
    rotation: Float = 0f,
    initialRotation: Float = 0f,
    offsetLeft: Float = 0f,
    offsetTop: Float = 0f
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Flame movement")
    val rotation by infiniteTransition.animateFloat(
        initialValue = initialRotation - rotation, // Starting rotation in degrees
        targetValue = initialRotation + rotation, // Ending rotation in degrees

        animationSpec = infiniteRepeatable(
            // Tween specifies the duration and easing
            animation = tween(200, easing = LinearEasing, delayMillis = delay),
            // RepeatMode.Reverse makes the animation go back and forth
            repeatMode = RepeatMode.Reverse
        ), label = "Flame movement"
    )

    val start = if (offsetLeft > 0f) offsetLeft else 0f
    val end = if (offsetLeft < 0f) offsetLeft * -1f else 0f
    val top = if (offsetTop > 0f) offsetTop else 0f
    val bottom = if (offsetTop < 0f) offsetTop * -1f else 0f

    Image(
        painter = painterResource(id = R.drawable.streak_flame),
        contentDescription = "Colored vector graphic",
        colorFilter = ColorFilter.tint(color),
        modifier = Modifier
            .padding(start.dp, top.dp, end.dp, bottom.dp)
            .fillMaxSize(size)
            .graphicsLayer(
                rotationZ = rotation,
                transformOrigin = TransformOrigin(0.5f, 1f)
            )
    )
}

@Composable
fun WoodComponent(
    size: Float,
    rotation: Float = 0f,
    offsetLeft: Float = 0f,
    offsetTop: Float = 0f){

    val start = if (offsetLeft > 0f) offsetLeft else 0f
    val end = if (offsetLeft < 0f) offsetLeft * -1f else 0f
    val top = if (offsetTop > 0f) offsetTop else 0f
    val bottom = if (offsetTop < 0f) offsetTop * -1f else 0f

    Image(
        painter = painterResource(id = R.drawable.streak_wood),
        contentDescription = "Wood vector graphic",
        modifier = Modifier
            .padding(start.dp, top.dp, end.dp, bottom.dp)
            .fillMaxSize(size)
            .graphicsLayer(
                rotationZ = rotation
            )
    )
}

@Composable
fun HalfCircle(progress: Float) {
    Canvas(
        modifier = Modifier
            .fillMaxSize(fraction = 0.5f) // Make the canvas half the size of the screen
            .aspectRatio(1f) // Ensure the canvas is square
    ) {
        val strokeWidth = 20.dp.toPx()
        val diameter = size.minDimension - strokeWidth
        val offset = strokeWidth / 2
        val style = Stroke(width = strokeWidth, cap = StrokeCap.Round)

        // Draw the static border arc
        drawArc(
            color = Color.LightGray,
            startAngle = 135f,
            sweepAngle = 270f,
            useCenter = false,
            topLeft = Offset(offset, offset),
            size = Size(diameter, diameter),
            style = style
        )

        // Adjust the gradient to be fully red only at complete progress
        val segmentCount = 100 // Control the smoothness of the gradient
        val progressSweepAngle = 270 * progress
        val segmentSweepAngle = progressSweepAngle / segmentCount

        for (i in 0 until segmentCount) {
            val fraction = if (progress < 1f) i / (segmentCount.toFloat() - 1) else 1f
            val segmentColor = lerpColor(Color.Yellow, Color.Red, fraction * progress)
            drawArc(
                color = segmentColor,
                startAngle = 135f + (segmentSweepAngle * i),
                sweepAngle = segmentSweepAngle,
                useCenter = false,
                topLeft = Offset(offset, offset),
                size = Size(diameter, diameter),
                style = style
            )
        }
    }
}

fun lerpColor(startColor: Color, endColor: Color, fraction: Float): Color {
    val r = lerp(startColor.red, endColor.red, fraction)
    val g = lerp(startColor.green, endColor.green, fraction)
    val b = lerp(startColor.blue, endColor.blue, fraction)
    return Color(red = r, green = g, blue = b, alpha = 1f)
}

fun lerp(start: Float, end: Float, fraction: Float): Float {
    return start + (end - start) * fraction
}

@Preview
@Composable
fun StreakCirclePreview() {
    StreakProgress()
}