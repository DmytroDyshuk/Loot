package com.example.loot.ui.viewmodel

import androidx.lifecycle.*
import com.example.loot.data.LootDao
import com.example.loot.model.Loot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LootViewModel(private val lootDao: LootDao): ViewModel() {

    val allLoot: LiveData<List<Loot>> = lootDao.getAllLoot().asLiveData()

    fun retrieveLoot(id: Long): LiveData<Loot> {
        return lootDao.getLoot(id).asLiveData()
    }

    fun addLoot(name: String, address: String, inSeason: Boolean, notes: String) {
        val loot = Loot(name = name, address = address, inSeason = inSeason, notes = notes)
        viewModelScope.launch(Dispatchers.IO) {
            lootDao.insertLoot(loot)
        }
    }

    fun updateLoot(id: Long, name: String, address: String, inSeason: Boolean, notes: String) {
        val loot = Loot(id = id, name = name, address = address, inSeason = inSeason, notes = notes)
        viewModelScope.launch(Dispatchers.IO) {
            lootDao.updateLoot(loot)
        }
    }

    fun deleteLoot(loot: Loot) {
        viewModelScope.launch(Dispatchers.IO) {
            lootDao.deleteLoot(loot)
        }
    }

    fun isValidEntry(name: String, address: String): Boolean {
        return name.isNotBlank() && address.isNotBlank()
    }

    class LootViewModelFactory(private val lootDao: LootDao): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LootViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LootViewModel(lootDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}