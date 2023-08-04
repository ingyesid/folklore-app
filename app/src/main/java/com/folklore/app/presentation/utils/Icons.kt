package com.folklore.app.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

class FolkloreIcons {
    companion object Rounded{
        @Composable
        fun Sort(): ImageVector {
            return remember {
                ImageVector.Builder(
                    name = "sort",
                    defaultWidth = 24.0.dp,
                    defaultHeight = 24.0.dp,
                    viewportWidth = 24.0f,
                    viewportHeight = 24.0f,
                ).apply {
                    path(
                        fill = SolidColor(Color.Black),
                        fillAlpha = 1f,
                        stroke = null,
                        strokeAlpha = 1f,
                        strokeLineWidth = 1.0f,
                        strokeLineCap = StrokeCap.Butt,
                        strokeLineJoin = StrokeJoin.Miter,
                        strokeLineMiter = 1f,
                        pathFillType = PathFillType.NonZero,
                    ) {
                        moveTo(8f, 18f)
                        horizontalLineTo(4f)
                        quadToRelative(-0.425f, 0f, -0.712f, -0.288f)
                        quadTo(3f, 17.425f, 3f, 17f)
                        reflectiveQuadToRelative(0.288f, -0.712f)
                        quadTo(3.575f, 16f, 4f, 16f)
                        horizontalLineToRelative(4f)
                        quadToRelative(0.425f, 0f, 0.713f, 0.288f)
                        quadTo(9f, 16.575f, 9f, 17f)
                        reflectiveQuadToRelative(-0.287f, 0.712f)
                        quadTo(8.425f, 18f, 8f, 18f)
                        close()
                        moveTo(20f, 8f)
                        horizontalLineTo(4f)
                        quadToRelative(-0.425f, 0f, -0.712f, -0.287f)
                        quadTo(3f, 7.425f, 3f, 7f)
                        reflectiveQuadToRelative(0.288f, -0.713f)
                        quadTo(3.575f, 6f, 4f, 6f)
                        horizontalLineToRelative(16f)
                        quadToRelative(0.425f, 0f, 0.712f, 0.287f)
                        quadTo(21f, 6.575f, 21f, 7f)
                        reflectiveQuadToRelative(-0.288f, 0.713f)
                        quadTo(20.425f, 8f, 20f, 8f)
                        close()
                        moveToRelative(-6f, 5f)
                        horizontalLineTo(4f)
                        quadToRelative(-0.425f, 0f, -0.712f, -0.288f)
                        quadTo(3f, 12.425f, 3f, 12f)
                        reflectiveQuadToRelative(0.288f, -0.713f)
                        quadTo(3.575f, 11f, 4f, 11f)
                        horizontalLineToRelative(10f)
                        quadToRelative(0.425f, 0f, 0.713f, 0.287f)
                        quadToRelative(0.287f, 0.288f, 0.287f, 0.713f)
                        reflectiveQuadToRelative(-0.287f, 0.712f)
                        quadTo(14.425f, 13f, 14f, 13f)
                        close()
                    }
                }.build()
            }
        }
    }
}
