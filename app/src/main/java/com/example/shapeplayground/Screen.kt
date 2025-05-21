package com.example.shapeplayground

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.circle
import androidx.graphics.shapes.toPath
import com.example.shapeplayground.ui.theme.ShapePlaygroundTheme

val resolutionRatio = 2.22f

@Composable
fun Screen(modifier: Modifier = Modifier) {

    val infiniteTransition = rememberInfiniteTransition(label = "morph")
    val morphProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "morphProgress"
    )

    Box(
        modifier = modifier
            .drawWithCache {
                val exagon = RoundedPolygon(
                    numVertices = 6,
                    radius = size.minDimension / 2,
                    centerX = size.width / 2,
                    centerY = size.height / 2
                )
                val circle = RoundedPolygon.circle(
                    radius = size.minDimension / 2,
                    centerX = size.width / 2,
                    centerY = size.height / 2
                )
                val basicPath = exagon.toComposePath()
                val morphPath = Morph(start = exagon, end = circle).toComposePath(progress = morphProgress)
                onDrawBehind {
                    drawPath(path = morphPath, color = Color.Cyan.copy(0.9f))
                    //scale(scale = 0.1f, pivot = Offset(size.width / 2, size.height / 2)) {
                        translate(left = -size.width / 2, top = -size.height / 2) {
                            scale(scale = 0.1f) {
                                translate(left = size.width / 2, top = size.height / (2 * resolutionRatio)) {
                                    drawPath(path = basicPath, color = Color.Red.copy(0.9f))
                                }
                            }
                        }
                    //}
                    //drawPath(path = path, color = Color.Red.copy(0.9f))
                }
            }.fillMaxSize()
    ) {
        //SideEffect { println("Testing recomposition: First!") }
    }
}

fun RoundedPolygon.toComposePath(): Path =
    this.toPath().asComposePath()

fun Morph.toComposePath(progress: Float): Path =
    this.toPath(progress = progress).asComposePath()

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    ShapePlaygroundTheme {
        Screen()
    }
}