package com.paisasplit.app.domain.ledger

import com.paisasplit.app.data.database.entities.LedgerEntry
import com.paisasplit.app.data.database.entities.LedgerEntryType
import com.paisasplit.app.data.database.entities.Settlement
import com.paisasplit.app.data.database.entities.Split
import com.paisasplit.app.data.database.entities.Transaction

class LedgerEngine {
    suspend fun createSplitTransactionEntries(
        transaction: Transaction,
        splits: List<Split>
    ): List<LedgerEntry> {
        val entries = mutableListOf<LedgerEntry>()

        entries.add(
            LedgerEntry(
                transactionId = transaction.id,
                entryType = LedgerEntryType.CASH_OUT,
                accountId = transaction.accountId,
                amountSigned = transaction.amountTotal.negate()
            )
        )

        val payerSplit = splits.find { it.memberId == transaction.payerMemberId }
        payerSplit?.let { split ->
            entries.add(
                LedgerEntry(
                    transactionId = transaction.id,
                    entryType = LedgerEntryType.EXPENSE,
                    categoryId = transaction.categoryId,
                    amountSigned = split.shareAmount
                )
            )
        }

        splits.filter { it.memberId != transaction.payerMemberId }
            .forEach { split ->
                entries.add(
                    LedgerEntry(
                        transactionId = transaction.id,
                        entryType = LedgerEntryType.RECEIVABLE_INC,
                        memberId = split.memberId,
                        amountSigned = split.shareAmount
                    )
                )
            }

        return entries
    }

    suspend fun createSettlementEntries(settlement: Settlement): List<LedgerEntry> {
        val txId = settlement.linkedTransactionId ?: return emptyList()
        return listOf(
            LedgerEntry(
                transactionId = txId,
                entryType = LedgerEntryType.CASH_IN,
                accountId = settlement.accountId,
                amountSigned = settlement.amount
            ),
            LedgerEntry(
                transactionId = txId,
                entryType = LedgerEntryType.RECEIVABLE_DEC,
                memberId = settlement.fromMemberId,
                amountSigned = settlement.amount.negate()
            )
        )
    }
}


