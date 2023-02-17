package com.example.loot.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.loot.model.Loot

@Database(entities = [Loot::class], version = 1, exportSchema = false)
abstract class LootDatabase: RoomDatabase() {

    abstract fun lootDao(): LootDao

    companion object {
        @Volatile
        private var INSTANCE: LootDatabase? = null

        fun getDatabase(context: Context): LootDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LootDatabase::class.java,
                    "loot_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}