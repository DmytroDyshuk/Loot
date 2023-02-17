package com.example.loot

import android.app.Application
import com.example.loot.data.LootDatabase

class BaseApplication : Application() {
    val database: LootDatabase by lazy { LootDatabase.getDatabase(this) }
}