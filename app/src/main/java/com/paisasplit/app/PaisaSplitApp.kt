package com.paisasplit.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.paisasplit.app.data.seeding.DataSeeder

@HiltAndroidApp
class PaisaSplitApp : Application() {
    @Inject lateinit var dataSeeder: DataSeeder

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                dataSeeder.seedIfEmpty()
            } catch (e: Exception) {
                // Log error but don't crash app
                e.printStackTrace()
            }
        }
    }
}


