package com.paisasplit.app.data.database.dao

import androidx.room.*
import com.paisasplit.app.data.database.entities.Account
import com.paisasplit.app.data.database.entities.Category
import com.paisasplit.app.data.database.entities.Group
import com.paisasplit.app.data.database.entities.Member
import com.paisasplit.app.data.database.entities.Transaction
import com.paisasplit.app.data.database.entities.Split
import com.paisasplit.app.data.database.entities.LedgerEntry
import com.paisasplit.app.data.database.entities.Settlement
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(account: Account)

    @Query("SELECT * FROM accounts")
    suspend fun getAllAccounts(): List<Account>

    @Query("SELECT * FROM accounts")
    fun observeAccounts(): Flow<List<Account>>
    
    @Query("UPDATE accounts SET currentBalance = :newBalance WHERE id = :accountId")
    suspend fun updateBalance(accountId: String, newBalance: java.math.BigDecimal)
}

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(category: Category)

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<Category>
}

@Dao
interface GroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(group: Group)

    @Query("SELECT * FROM groups")
    suspend fun getAllGroups(): List<Group>
}

@Dao
interface MemberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(member: Member)

    @Query("SELECT * FROM members WHERE groupId = :groupId")
    suspend fun getMembers(groupId: String): List<Member>

    @Query("SELECT * FROM members")
    suspend fun getAllMembers(): List<Member>
}

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(transaction: Transaction)

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    suspend fun getAllTransactions(): List<Transaction>

    @Query("SELECT * FROM transactions ORDER BY date DESC LIMIT :limit")
    fun observeRecentTransactions(limit: Int): Flow<List<Transaction>>
}

@Dao
interface SplitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(split: Split)

    @Query("SELECT * FROM splits WHERE transactionId = :transactionId")
    suspend fun getSplits(transactionId: String): List<Split>

    @Query("SELECT * FROM splits")
    suspend fun getAllSplits(): List<Split>
}

@Dao
interface LedgerEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entry: LedgerEntry)
}

@Dao
interface SettlementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(settlement: Settlement)

    @Query("SELECT * FROM settlements")
    suspend fun getAllSettlements(): List<Settlement>
}


