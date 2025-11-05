package com.lazyprogramerr.floatingbottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val ButtonBlack = Color(0xFF121212)
val ButtonBlue = Color(0xFF15429F)
val ButtonOrange = Color(0xFFEF8E27)

enum class UiAlignment {
    Start, End, Top, Bottom, Center,
    CenterStart, CenterEnd, CenterTop, CenterBottom,
    BottomEnd, TopEnd
}

@Composable
fun ShapableButton(
    modifier: Modifier = Modifier,
    iconRes: Painter? = null,
    text: String? = null,
    contentDescription: String? = null,
    iconSize: Dp = 22.dp,
    textSize: TextUnit = 17.sp,
    shape: Shape = RoundedCornerShape(12.dp),
    iconColor: Color = ButtonBlack,
    textColor: Color = ButtonBlack,
    onClickColor: Color = ButtonOrange,
    backgroundColor: Color = ButtonBlue,
    strokeColor: Color = ButtonBlack,
    uiAlignment: UiAlignment = UiAlignment.Center,
    isSelected: Boolean = false,
    spacing: Dp = 6.dp,
    strokeWidth: Dp = 2.dp,
    onClick: () -> Unit
) {
    val interaction = remember { MutableInteractionSource() }

    val bgColor = if (isSelected) onClickColor else backgroundColor

    Box(
        modifier = modifier
            .background(bgColor, shape)
            .border(strokeWidth, strokeColor, shape)
            .clickable(
                interactionSource = interaction,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 14.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {

        val horizontal = when(uiAlignment) {
            UiAlignment.Start, UiAlignment.CenterStart -> Alignment.Start
            UiAlignment.End, UiAlignment.CenterEnd, UiAlignment.TopEnd, UiAlignment.BottomEnd -> Alignment.End
            else -> Alignment.CenterHorizontally
        }

        val vertical = when(uiAlignment) {
            UiAlignment.Top, UiAlignment.CenterTop, UiAlignment.TopEnd -> Alignment.Top
            UiAlignment.Bottom, UiAlignment.CenterBottom, UiAlignment.BottomEnd -> Alignment.Bottom
            else -> Alignment.CenterVertically
        }

        val isColumn = uiAlignment in listOf(
            UiAlignment.Top, UiAlignment.CenterTop, UiAlignment.TopEnd,
            UiAlignment.Bottom, UiAlignment.CenterBottom, UiAlignment.BottomEnd
        )

        if (isColumn) {
            Column(horizontalAlignment = horizontal, verticalArrangement = Arrangement.Center) {
                if (uiAlignment in listOf(UiAlignment.Bottom, UiAlignment.CenterBottom, UiAlignment.BottomEnd))
                    text?.let { Text(it, fontSize = textSize, color = textColor) }

                if (iconRes != null && text != null) Spacer(Modifier.height(spacing))

                iconRes?.let {
                    Icon(it, contentDescription, tint = iconColor, modifier = Modifier.size(iconSize))
                }

                if (uiAlignment !in listOf(UiAlignment.Bottom, UiAlignment.CenterBottom, UiAlignment.BottomEnd))
                    text?.let { Text(it, fontSize = textSize, color = textColor) }
            }
        } else {
            Row(verticalAlignment = vertical, horizontalArrangement = Arrangement.Center) {
                if (uiAlignment in listOf(UiAlignment.End, UiAlignment.CenterEnd))
                    text?.let { Text(it, fontSize = textSize, color = textColor) }

                if (iconRes != null && text != null) Spacer(Modifier.width(spacing))

                iconRes?.let {
                    Icon(it, contentDescription, tint = iconColor, modifier = Modifier.size(iconSize))
                }

                if (uiAlignment !in listOf(UiAlignment.End, UiAlignment.CenterEnd))
                    text?.let { Text(it, fontSize = textSize, color = textColor) }
            }
        }
    }
}




//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewButtons() {
//    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
//        ShapableButton(
//            text = "Icon\nhello \n user\n click here",
//            uiAlignment = UiAlignment.Start,
//            onClick = {},
//            iconRes = painterResource(android.R.drawable.star_big_on)
//        )
//        ShapableButton(
//            text = "Icon\nhello \n user\n click here",
//            uiAlignment = UiAlignment.TopEnd,
//            onClick = {},
//            iconRes = painterResource(android.R.drawable.star_big_on)
//        )
//        ShapableButton(
//            text = "Icon\nhello \n user\n click here",
//            uiAlignment = UiAlignment.End,
//            onClick = {},
//            iconRes = painterResource(android.R.drawable.star_big_on)
//        )
//        ShapableButton(
////            text = "Icon\nhello \n user\n click here",
//            uiAlignment = UiAlignment.Bottom,
//            onClick = {},
//            iconRes = painterResource(android.R.drawable.star_big_on)
//        )
//        ShapableButton(
//            modifier = Modifier.padding(2.dp),
//            text = "Icon\nhello \n user\n click here",
//            uiAlignment = UiAlignment.BottomEnd,
//            onClick = {},
//            iconRes = painterResource(android.R.drawable.star_big_on),
//            shape = RoundedCornerShape(20)
//        )
//    }
//}