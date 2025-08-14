package com.paisasplit.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paisasplit.app.data.database.dao.AccountDao
import com.paisasplit.app.data.database.dao.CategoryDao
import com.paisasplit.app.data.database.dao.GroupDao
import com.paisasplit.app.data.database.dao.MemberDao
import com.paisasplit.app.data.database.dao.TransactionDao
import com.paisasplit.app.data.database.dao.SplitDao
import com.paisasplit.app.data.database.dao.LedgerEntryDao
import com.paisasplit.app.data.database.dao.SettlementDao
import com.paisasplit.app.data.database.entities.Account
import com.paisasplit.app.data.database.entities.Category
import com.paisasplit.app.data.database.entities.Group
import com.paisasplit.app.data.database.entities.Member
import com.paisasplit.app.data.database.entities.Transaction
import com.paisasplit.app.data.database.entities.Split
import com.paisasplit.app.data.database.entities.LedgerEntry
import com.paisasplit.app.data.database.entities.Settlement
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [
        Account::class,
        Category::class,
        Group::class,
        Member::class,
        Transaction::class,
        Split::class,
        LedgerEntry::class,
        Settlement::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PaisaDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun groupDao(): GroupDao
    abstract fun memberDao(): MemberDao
    abstract fun transactionDao(): TransactionDao
    abstract fun splitDao(): SplitDao
    abstract fun ledgerEntryDao(): LedgerEntryDao
    abstract fun settlementDao(): SettlementDao

    companion object {
        @Volatile
        private var INSTANCE: PaisaDatabase? = null

        fun getDatabase(context: Context): PaisaDatabase {
            return INSTANCE ?: synchronized(this) {
                val passphrase = getOrCreatePassphrase(context)
                val factory = SupportFactory(passphrase)
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PaisaDatabase::class.java,
                    "paisa_database"
                )
                    .openHelperFactory(factory)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private fun getOrCreatePassphrase(context: Context): ByteArray {
            // For now, use a static passphrase. Replace with EncryptedSharedPreferences + Keystore storage.
            SQLiteDatabase.loadLibs(context)
            return "paisa-default-passphrase".toByteArray()
        }
    }
}


