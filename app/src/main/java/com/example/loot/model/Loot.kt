package com.example.loot.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loot_database")
data class Loot(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val address: String,
    @ColumnInfo(name = "in_season")
    val inSeason: Boolean,
    val notes: String?
)