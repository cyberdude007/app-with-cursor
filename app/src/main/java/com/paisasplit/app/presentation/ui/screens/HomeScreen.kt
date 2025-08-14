package com.paisasplit.app.presentation.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paisasplit.app.presentation.viewmodel.HomeViewModel
import com.paisasplit.app.presentation.ui.components.AnimatedBalanceCard
import com.paisasplit.app.presentation.ui.components.QuickActionButton
import com.paisasplit.app.presentation.ui.components.TransactionItem
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
    }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Balance Cards with staggered animations
        item {
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInHorizontally(
                    animationSpec = tween(600, delayMillis = 200),
                    initialOffsetX = { -it }
                ) + fadeIn(animationSpec = tween(600, delayMillis = 200))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AnimatedBalanceCard(
                        title = "Total Balance",
                        amount = uiState.totalBalance,
                        subtitle = "${uiState.accountCount} accounts",
                        modifier = Modifier.weight(1f),
                        delay = 300
                    )
                    AnimatedBalanceCard(
                        title = "Net Worth",
                        amount = uiState.netWorth,
                        subtitle = "Including receivables",
                        modifier = Modifier.weight(1f),
                        delay = 400
                    )
                }
            }
        }

        // Quick Actions Card
        item {
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    animationSpec = tween(600, delayMillis = 500),
                    initialOffsetY = { it / 2 }
                ) + fadeIn(animationSpec = tween(600, delayMillis = 500)) + scaleIn(
                    animationSpec = tween(600, delayMillis = 500),
                    initialScale = 0.9f
                )
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        QuickActionButton(
                            icon = Icons.Default.Add,
                            label = "Add Expense",
                            onClick = { /* Navigate to add expense */ }
                        )
                        QuickActionButton(
                            icon = Icons.Default.Group,
                            label = "Groups",
                            onClick = { /* Navigate to groups */ }
                        )
                    }
                }
            }
        }

        // Recent Transactions
        item {
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    animationSpec = tween(600, delayMillis = 700),
                    initialOffsetY = { it / 2 }
                ) + fadeIn(animationSpec = tween(600, delayMillis = 700))
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Recent Transactions",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        if (uiState.recentTransactions.isEmpty()) {
                            Text(
                                text = "No transactions yet",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            uiState.recentTransactions.take(5).forEach { transaction ->
                                TransactionItem(
                                    transaction = transaction,
                                    onClick = { /* Navigate to transaction details */ }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}


