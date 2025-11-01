package com.example.chrometabscreens.designsystem.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chrometabscreens.designsystem.icon.ApplicationIcon

@Composable
fun ChromeCard(
    onClearButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    var isVisible by rememberSaveable { mutableStateOf(false) }
    var isDeleteButtonPressed by remember { mutableStateOf(false) }
    var isSelected by remember { mutableStateOf(false) }

    val animatedOffsetY by animateFloatAsState(
        targetValue = if (isVisible) 0f else 6f,
        animationSpec = tween(300, easing = FastOutLinearInEasing),
        label = "offsetY"
    )
    val animatedScale by animateFloatAsState(
        targetValue = if (isDeleteButtonPressed) 0f else 1f,
        animationSpec = tween(300, easing = FastOutSlowInEasing),
        finishedListener = {
            if (isDeleteButtonPressed) {
                onClearButtonClick()
            }
        },
        label = "scale"
    )
    val animatedBlur by animateDpAsState(
        targetValue = if (isVisible) 0.dp else 6.dp,
        animationSpec = tween(300, easing = FastOutLinearInEasing),
        label = "blur"
    )

    val selectedModifier = modifier
        .fillMaxWidth()
        .height(260.dp)
        .graphicsLayer(
            scaleX = animatedScale,
            scaleY = animatedScale,
            translationY = animatedOffsetY
        )
        .background(
            MaterialTheme.colorScheme.tertiary, RoundedCornerShape(22.dp)
        )
        .clip(RoundedCornerShape(22.dp))
        .clickable { isSelected = !isSelected }
        .blur(animatedBlur)
        .then(
            if (isSelected) {
                Modifier.border(2.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(22.dp))
            } else {
                Modifier
            }
        )

    LaunchedEffect(Unit) {
        isVisible = true
    }

    Box(selectedModifier) {
        TopCardContent(
            onClearButtonClick = {
                isDeleteButtonPressed = true
            }
        )
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
private fun TopCardContent(
    onClearButtonClick: () -> Unit,
) {
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(.16f)
            .background(
                MaterialTheme.colorScheme.onTertiary,
            )
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Row(
                    Modifier
                        .padding(start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.tertiary)
                    )
                    Spacer(Modifier.width(8.dp))
                    Box(
                        Modifier
                            .fillMaxHeight(.2f)
                            .fillMaxWidth(.66f)
                            .clip(RectangleShape)
                            .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(4.dp))
                    )
                }
            }

            IconButton(onClearButtonClick) {
                Icon(
                    painterResource(ApplicationIcon.Clear),
                    "Clear",
                    tint = MaterialTheme.colorScheme.scrim
                )
            }
        }
    }
}
