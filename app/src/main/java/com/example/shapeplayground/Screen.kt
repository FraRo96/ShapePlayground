package com.example.shapeplayground

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import com.example.shapeplayground.ui.theme.ShapePlaygroundTheme

@Composable
fun Screen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .drawWithCache {
                val roundedPolygon = RoundedPolygon(
                    numVertices = 6,
                    radius = size.minDimension / 2,
                    centerX = size.width / 2,
                    centerY = size.height / 2
                )
                val path = roundedPolygon.toComposePath()
                onDrawBehind {
                    drawPath(path = path, color = Color.Cyan.copy(0.9f))
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