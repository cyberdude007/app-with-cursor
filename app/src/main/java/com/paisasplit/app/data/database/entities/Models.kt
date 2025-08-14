package com.paisasplit.app.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.UUID

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val type: AccountType,
    val openingBalance: BigDecimal,
    val currentBalance: BigDecimal,
    val archived: Boolean = false,
    val icon: String,
    val color: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val type: TransactionType,
    val icon: String,
    val color: String,
    val parentId: String? = null
)

@Entity(tableName = "labels")
data class Label(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val color: String
)

@Entity(tableName = "groups")
data class Group(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String = "",
    val archived: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "members")
data class Member(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val groupId: String,
    val displayName: String,
    val notes: String = "",
    val isSelf: Boolean = false,
    val avatarColor: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val kind: TransactionKind,
    val title: String,
    val note: String = "",
    val accountId: String,
    val categoryId: String?,
    val amountTotal: BigDecimal,
    val currency: String = "INR",
    val date: Long,
    val groupId: String?,
    val payerMemberId: String?,
    val attachments: List<String> = emptyList(),
    val deleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "splits")
data class Split(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val transactionId: String,
    val memberId: String,
    val shareAmount: BigDecimal,
    val sharePercent: Double? = null,
    val shareCount: Int? = null,
    val included: Boolean = true,
    val status: SplitStatus = SplitStatus.OPEN,
    val settledAmount: BigDecimal = BigDecimal.ZERO
)

@Entity(tableName = "ledger_entries")
data class LedgerEntry(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val transactionId: String,
    val entryType: LedgerEntryType,
    val accountId: String? = null,
    val memberId: String? = null,
    val categoryId: String? = null,
    val amountSigned: BigDecimal,
    val currency: String = "INR",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "settlements")
data class Settlement(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val groupId: String,
    val fromMemberId: String,
    val toMemberId: String,
    val accountId: String?,
    val amount: BigDecimal,
    val method: SettlementMethod,
    val date: Long,
    val note: String = "",
    val linkedTransactionId: String?
)


