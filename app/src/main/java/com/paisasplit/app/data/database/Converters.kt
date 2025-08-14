package com.paisasplit.app.data.database

import androidx.room.TypeConverter
import com.paisasplit.app.data.database.entities.AccountType
import com.paisasplit.app.data.database.entities.TransactionType
import com.paisasplit.app.data.database.entities.TransactionKind
import com.paisasplit.app.data.database.entities.SplitStatus
import com.paisasplit.app.data.database.entities.LedgerEntryType
import com.paisasplit.app.data.database.entities.SettlementMethod
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset

class Converters {
    @TypeConverter
    fun fromBigDecimal(value: BigDecimal?): String? = value?.toPlainString()

    @TypeConverter
    fun toBigDecimal(value: String?): BigDecimal? = value?.let { BigDecimal(it) }

    @TypeConverter
    fun fromStringList(list: List<String>?): String? = list?.joinToString("|")

    @TypeConverter
    fun toStringList(serialized: String?): List<String>? = serialized?.split('|')?.filter { it.isNotEmpty() }

    @TypeConverter
    fun fromAccountType(value: AccountType?): String? = value?.name

    @TypeConverter
    fun toAccountType(value: String?): AccountType? = value?.let { AccountType.valueOf(it) }

    @TypeConverter
    fun fromTransactionType(value: TransactionType?): String? = value?.name

    @TypeConverter
    fun toTransactionType(value: String?): TransactionType? = value?.let { TransactionType.valueOf(it) }

    @TypeConverter
    fun fromTransactionKind(value: TransactionKind?): String? = value?.name

    @TypeConverter
    fun toTransactionKind(value: String?): TransactionKind? = value?.let { TransactionKind.valueOf(it) }

    @TypeConverter
    fun fromSplitStatus(value: SplitStatus?): String? = value?.name

    @TypeConverter
    fun toSplitStatus(value: String?): SplitStatus? = value?.let { SplitStatus.valueOf(it) }

    @TypeConverter
    fun fromLedgerEntryType(value: LedgerEntryType?): String? = value?.name

    @TypeConverter
    fun toLedgerEntryType(value: String?): LedgerEntryType? = value?.let { LedgerEntryType.valueOf(it) }

    @TypeConverter
    fun fromSettlementMethod(value: SettlementMethod?): String? = value?.name

    @TypeConverter
    fun toSettlementMethod(value: String?): SettlementMethod? = value?.let { SettlementMethod.valueOf(it) }
}


