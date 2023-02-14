package com.example.loot.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.loot.R
import com.example.loot.databinding.FragmentLootListBinding

class LootListFragment: Fragment() {

    private var _binding: FragmentLootListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLootListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
    }

    private fun bind() {
        binding.apply {
            fabAddLoot.setOnClickListener {
                findNavController().navigate(R.id.action_lootListFragment_to_addLootFragment)
            }
        }
    }

}