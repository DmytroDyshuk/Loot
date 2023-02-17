package com.example.loot.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.loot.databinding.ListItemLootBinding
import com.example.loot.model.Loot

class LootListAdapter(private val clickListener: (Loot) -> Unit) : ListAdapter<Loot, LootListAdapter.LootViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LootViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LootViewHolder(ListItemLootBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: LootViewHolder, position: Int) {
        val loot = getItem(position)
        holder.itemView.setOnClickListener {
            clickListener(loot)
        }
        holder.bind(loot)
    }

    class LootViewHolder(private var binding: ListItemLootBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(loot: Loot) {
            binding.loot = loot
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Loot>() {
        override fun areItemsTheSame(oldItem: Loot, newItem: Loot) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Loot, newItem: Loot) = oldItem == newItem
    }
}