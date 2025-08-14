package com.paisasplit.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paisasplit.app.data.repository.PaisaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.math.BigDecimal
import javax.inject.Inject

data class HomeUiState(
    val totalBalance: BigDecimal = BigDecimal.ZERO,
    val netWorth: BigDecimal = BigDecimal.ZERO,
    val accountCount: Int = 0,
    val recentTransactions: List<com.hisaabsplit.app.data.database.entities.Transaction> = emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PaisaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        combine(
            repository.observeAccounts(),
            repository.observeRecentTransactions(limit = 10)
        ) { accounts, transactions ->
            val total = accounts.fold(BigDecimal.ZERO) { acc, a -> acc + a.currentBalance }
            val netWorth = total // Placeholder, would add receivables later
            _uiState.value = HomeUiState(
                totalBalance = total,
                netWorth = netWorth,
                accountCount = accounts.size,
                recentTransactions = transactions
            )
        }.launchIn(viewModelScope)
    }
}


