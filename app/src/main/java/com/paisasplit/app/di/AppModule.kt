package com.paisasplit.app.di

import android.content.Context
import com.paisasplit.app.data.database.PaisaDatabase
import com.paisasplit.app.data.repository.PaisaRepository
import com.paisasplit.app.domain.ledger.LedgerEngine
import com.paisasplit.app.domain.split.SplitCalculator
import com.paisasplit.app.data.seeding.DataSeeder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PaisaDatabase = PaisaDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun provideLedgerEngine(): LedgerEngine = LedgerEngine()

    @Provides
    @Singleton
    fun provideRepository(database: PaisaDatabase, ledgerEngine: LedgerEngine): PaisaRepository =
        PaisaRepository(database, ledgerEngine)

    @Provides
    @Singleton
    fun provideSplitCalculator(): SplitCalculator = SplitCalculator()
    
    @Provides
    @Singleton
    fun provideDataSeeder(repository: PaisaRepository): DataSeeder = DataSeeder(repository)
}


