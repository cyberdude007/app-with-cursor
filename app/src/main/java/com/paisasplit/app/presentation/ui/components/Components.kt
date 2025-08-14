package com.paisasplit.app.presentation.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun AmountInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Amount",
    prefix: String = "₹",
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    
    AnimatedVisibility(
        visible = true,
        enter = expandVertically(animationSpec = tween(300)) + fadeIn(animationSpec = tween(300)),
        exit = shrinkVertically(animationSpec = tween(300)) + fadeOut(animationSpec = tween(300))
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.matches(Regex("^\\d*\\.?\\d{0,2}$"))) {
                    onValueChange(newValue)
                }
            },
            label = { 
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium
                ) 
            },
            leadingIcon = {
                AnimatedContent(
                    targetState = prefix,
                    transitionSpec = {
                        slideInVertically { -it } + fadeIn() with slideOutVertically { it } + fadeOut()
                    }
                ) { targetPrefix ->
                    Text(
                        text = targetPrefix,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = if (isFocused) 1.02f else 1f
                    scaleY = if (isFocused) 1.02f else 1f
                }
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedLabelColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(12.dp),
            onValueChange = { onValueChange(it) },
            onFocusChanged = { isFocused = it.isFocused }
        )
    }
}

@Composable
fun MemberAvatar(
    name: String,
    color: Color,
    size: Dp = 40.dp,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(
                color = color.copy(alpha = if (isPressed) 0.3f else 0.2f),
                shape = CircleShape
            )
            .shadow(
                elevation = if (isPressed) 2.dp else 4.dp,
                shape = CircleShape,
                spotColor = color.copy(alpha = 0.3f)
            )
            .graphicsLayer {
                scaleX = if (isPressed) 0.95f else 1f
                scaleY = if (isPressed) 0.95f else 1f
            }
            .then(if (onClick != null) Modifier.clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() } else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name.take(2).uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = color,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun BalanceCard(
    title: String,
    amount: BigDecimal,
    subtitle: String? = null,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = if (isPressed) 0.98f else 1f
                scaleY = if (isPressed) 0.98f else 1f
                shadowElevation = if (isPressed) 2.dp.toPx() else 8.dp.toPx()
            }
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(
                    animationSpec = tween(400, delayMillis = 100)
                ) { it / 2 } + fadeIn(
                    animationSpec = tween(400, delayMillis = 100)
                )
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            AnimatedContent(
                targetState = amount,
                transitionSpec = {
                    slideInVertically { -it } + fadeIn() with slideOutVertically { it } + fadeOut()
                }
            ) { targetAmount ->
                Text(
                    text = "₹${targetAmount.setScale(2, RoundingMode.HALF_UP)}",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
            
            subtitle?.let {
                Spacer(modifier = Modifier.height(4.dp))
                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically(
                        animationSpec = tween(400, delayMillis = 200)
                    ) { it / 2 } + fadeIn(
                        animationSpec = tween(400, delayMillis = 200)
                    )
                ) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Composable
fun AnimatedBalanceCard(
    title: String,
    amount: BigDecimal,
    subtitle: String? = null,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    delay: Int = 0
) {
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(delay.toLong())
        isVisible = true
    }
    
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(
            animationSpec = tween(600, delayMillis = delay.toLong()),
            initialOffsetX = { if (delay % 2 == 0) -it else it }
        ) + fadeIn(
            animationSpec = tween(600, delayMillis = delay.toLong())
        ) + scaleIn(
            animationSpec = tween(600, delayMillis = delay.toLong()),
            initialScale = 0.8f
        ),
        exit = slideOutHorizontally(
            animationSpec = tween(300),
            targetOffsetX = { if (delay % 2 == 0) -it else it }
        ) + fadeOut(animationSpec = tween(300)) + scaleOut(
            animationSpec = tween(300),
            targetScale = 0.8f
        )
    ) {
        BalanceCard(
            title = title,
            amount = amount,
            subtitle = subtitle,
            onClick = onClick,
            modifier = modifier
        )
    }
}


