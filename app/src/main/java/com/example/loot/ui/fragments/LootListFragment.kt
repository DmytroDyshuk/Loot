package com.example.loot.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loot.BaseApplication
import com.example.loot.R
import com.example.loot.databinding.FragmentLootListBinding
import com.example.loot.ui.adapter.LootListAdapter
import com.example.loot.ui.viewmodel.LootViewModel

class LootListFragment: Fragment() {

    private val viewModel: LootViewModel by activityViewModels {
        LootViewModel.LootViewModelFactory((activity?.application as BaseApplication).database.lootDao())
    }

    private var _binding: FragmentLootListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLootListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
        val adapter = LootListAdapter { loot ->
            val action = LootListFragmentDirections.actionLootListFragmentToLootDetailsFragment(loot.id)
            findNavController().navigate(action)
        }
        binding.rvLootList.adapter = adapter
        binding.rvLootList.layoutManager = LinearLayoutManager(this.context)
        viewModel.allLoot.observe(this.viewLifecycleOwner) { listLoot ->
            listLoot.let {
                adapter.submitList(it)
            }
        }
    }

    private fun bind() {
        binding.apply {
            fabAddLoot.setOnClickListener {
                findNavController().navigate(R.id.action_lootListFragment_to_addLootFragment)
            }
        }
    }

}