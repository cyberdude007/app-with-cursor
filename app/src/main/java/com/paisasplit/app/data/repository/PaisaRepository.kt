package com.paisasplit.app.data.repository

import com.paisasplit.app.data.database.PaisaDatabase
import com.paisasplit.app.data.database.entities.Account
import com.paisasplit.app.data.database.entities.Group
import com.paisasplit.app.data.database.entities.Member
import com.paisasplit.app.data.database.entities.Transaction
import com.paisasplit.app.data.database.entities.Split
import com.paisasplit.app.data.database.entities.LedgerEntry
import com.paisasplit.app.domain.ledger.LedgerEngine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PaisaRepository(
    private val database: PaisaDatabase,
    private val ledgerEngine: LedgerEngine
) {
    suspend fun insertAccount(account: Account) = database.accountDao().upsert(account)
    suspend fun insertGroup(group: Group) = database.groupDao().upsert(group)
    suspend fun insertMembers(members: List<Member>) {
        members.forEach { database.memberDao().upsert(it) }
    }

    suspend fun createSplitTransaction(transaction: Transaction, splits: List<Split>) {
        database.transactionDao().upsert(transaction)
        splits.forEach { database.splitDao().upsert(it) }
        val entries = ledgerEngine.createSplitTransactionEntries(transaction, splits)
        entries.forEach { database.ledgerEntryDao().upsert(it) }
        
        // Update account balance
        val account = getAccount(transaction.accountId)
        val newBalance = account.currentBalance.subtract(transaction.amountTotal)
        database.accountDao().updateBalance(account.id, newBalance)
    }

    suspend fun getAccount(id: String): Account = database.accountDao().getAllAccounts().first { it.id == id }
    suspend fun getMember(memberId: String): Member = database.memberDao().getAllMembers().first { it.id == memberId }
    suspend fun getMemberBalance(memberId: String): java.math.BigDecimal = java.math.BigDecimal.ZERO
    suspend fun getGroup(groupId: String): Group = database.groupDao().getAllGroups().first { it.id == groupId }

    // Observables
    fun observeAccounts(): Flow<List<Account>> = database.accountDao().observeAccounts()
    fun observeRecentTransactions(limit: Int): Flow<List<Transaction>> = database.transactionDao().observeRecentTransactions(limit)

    // One-shot fetches for setup screens
    suspend fun getAllAccounts(): List<Account> = database.accountDao().getAllAccounts()
    suspend fun getAllGroups(): List<Group> = database.groupDao().getAllGroups()
    suspend fun getMembers(groupId: String): List<Member> = database.memberDao().getMembers(groupId)
}


