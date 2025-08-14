package com.paisasplit.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paisasplit.app.data.database.entities.Account
import com.paisasplit.app.data.database.entities.Group
import com.paisasplit.app.data.database.entities.Member
import com.paisasplit.app.data.database.entities.SplitMode
import com.paisasplit.app.data.repository.PaisaRepository
import com.paisasplit.app.domain.split.SplitCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

data class AddSplitUiState(
    val amount: String = "",
    val accounts: List<Account> = emptyList(),
    val groups: List<Group> = emptyList(),
    val selectedAccount: Account? = null,
    val selectedGroup: Group? = null,
    val availableMembers: List<Member> = emptyList(),
    val selectedMembers: List<Member> = emptyList(),
    val memberSearchQuery: String = "",
    val splitMode: SplitMode = SplitMode.EQUAL,
    val customAmounts: Map<String, String> = emptyMap(),
    val payerIncluded: Boolean = true,
    val canSave: Boolean = false
)

@HiltViewModel
class AddSplitViewModel @Inject constructor(
    private val repository: PaisaRepository,
    private val splitCalculator: SplitCalculator
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddSplitUiState())
    val uiState: StateFlow<AddSplitUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val accounts = repository.getAllAccounts()
            val groups = repository.getAllGroups()
            _uiState.update {
                it.copy(
                    accounts = accounts,
                    groups = groups,
                    selectedAccount = accounts.firstOrNull(),
                    selectedGroup = groups.firstOrNull()
                )
            }
            loadMembersForSelectedGroup()
        }
    }

    private suspend fun loadMembersForSelectedGroup() {
        val group = _uiState.value.selectedGroup ?: return
        val members = repository.getMembers(group.id)
        _uiState.update { it.copy(availableMembers = members, selectedMembers = members) }
        recomputeCanSave()
    }

    fun updateAmount(value: String) {
        _uiState.update { it.copy(amount = value) }
        recomputeCanSave()
    }

    fun selectAccount(account: Account) {
        _uiState.update { it.copy(selectedAccount = account) }
        recomputeCanSave()
    }

    fun selectGroup(group: Group) {
        _uiState.update { it.copy(selectedGroup = group) }
        viewModelScope.launch { loadMembersForSelectedGroup() }
    }

    fun updateMemberSearch(query: String) {
        _uiState.update { it.copy(memberSearchQuery = query) }
    }

    fun toggleMember(member: Member) {
        _uiState.update { state ->
            val selected = state.selectedMembers.toMutableList()
            if (selected.any { it.id == member.id }) selected.removeAll { it.id == member.id } else selected.add(member)
            state.copy(selectedMembers = selected)
        }
        recomputeCanSave()
    }

    fun selectSplitMode(mode: SplitMode) {
        _uiState.update { it.copy(splitMode = mode) }
        recomputeCanSave()
    }

    fun updateCustomAmount(memberId: String, value: String) {
        _uiState.update { it.copy(customAmounts = it.customAmounts.toMutableMap().apply { put(memberId, value) }) }
        recomputeCanSave()
    }

    fun setPayerIncluded(included: Boolean) {
        _uiState.update { it.copy(payerIncluded = included) }
    }

    private fun recomputeCanSave() {
        val amountOk = _uiState.value.amount.toBigDecimalOrNull()?.let { it > BigDecimal.ZERO } == true
        val accountOk = _uiState.value.selectedAccount != null
        val membersOk = _uiState.value.selectedMembers.size >= 1
        _uiState.update { it.copy(canSave = amountOk && accountOk && membersOk) }
    }

    fun saveSplitTransaction() {
        val state = _uiState.value
        val total = state.amount.toBigDecimalOrNull() ?: return
        val account = state.selectedAccount ?: return
        val group = state.selectedGroup
        val payer = state.selectedMembers.firstOrNull { it.isSelf } ?: state.selectedMembers.firstOrNull()
        if (payer == null) return

        viewModelScope.launch {
            val transaction = Transaction(
                kind = TransactionKind.SPLIT,
                title = "Split Expense",
                accountId = account.id,
                categoryId = null,
                amountTotal = total,
                groupId = group?.id,
                payerMemberId = payer.id,
                date = System.currentTimeMillis()
            )

            val amounts: Map<String, BigDecimal> = when (state.splitMode) {
                SplitMode.EQUAL -> {
                    val list = splitCalculator.calculateEqualSplit(total, state.selectedMembers.size, state.payerIncluded)
                    state.selectedMembers.mapIndexed { idx, member -> member.id to list.getOrElse(idx) { BigDecimal.ZERO } }.toMap()
                }
                SplitMode.CUSTOM_AMOUNTS -> {
                    state.selectedMembers.associate { it.id to (state.customAmounts[it.id]?.toBigDecimalOrNull() ?: BigDecimal.ZERO) }
                }
                else -> state.selectedMembers.associate { it.id to BigDecimal.ZERO }
            }

            val splits = state.selectedMembers.map { member ->
                Split(
                    transactionId = transaction.id,
                    memberId = member.id,
                    shareAmount = amounts[member.id] ?: BigDecimal.ZERO
                )
            }

            repository.createSplitTransaction(transaction, splits)
        }
    }
}


