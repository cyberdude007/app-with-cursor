package com.paisasplit.app.data.database.entities

enum class AccountType { CASH, BANK, WALLET, CARD, INVESTMENT }
enum class TransactionType { EXPENSE, INCOME }
enum class TransactionKind { NORMAL, SPLIT, TRANSFER, SETTLEMENT }
enum class SplitStatus { OPEN, SETTLED, WAIVED }
enum class SplitMode { EQUAL, CUSTOM_AMOUNTS, PERCENTAGES, SHARES, ITEMIZED }
enum class LedgerEntryType {
    CASH_OUT, CASH_IN, EXPENSE, INCOME,
    RECEIVABLE_INC, RECEIVABLE_DEC,
    WRITEOFF, TRANSFER_IN, TRANSFER_OUT
}
enum class SettlementMethod { UPI, CASH, BANK_TRANSFER, OTHER }


