package com.example.loot.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.loot.BaseApplication
import com.example.loot.R
import com.example.loot.databinding.FragmentAddLootBinding
import com.example.loot.model.Loot
import com.example.loot.ui.viewmodel.LootViewModel

class AddLootFragment: Fragment() {

    private lateinit var loot: Loot

    private val navigationArgs: AddLootFragmentArgs by navArgs()

    private val viewModel: LootViewModel by activityViewModels {
        LootViewModel.LootViewModelFactory((activity?.application as BaseApplication).database.lootDao())
    }

    private var _binding: FragmentAddLootBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddLootBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        if (id > 0) {
            viewModel.retrieveLoot(id).observe(this.viewLifecycleOwner) {
                loot = it
                bindLoot()
            }
            binding.btnDelete.visibility = View.VISIBLE
            binding.btnDelete.setOnClickListener {
                deleteLoot(loot)
            }
        } else {
            binding.btnSave.setOnClickListener {
                addLoot()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindLoot() {
        binding.apply {
            titNameInput.setText(loot.name, TextView.BufferType.SPANNABLE)
            titLocationInput.setText(loot.address, TextView.BufferType.SPANNABLE)
            cbCurrentlyInSeason.isChecked = loot.inSeason
            titNotesInput.setText(loot.notes, TextView.BufferType.SPANNABLE)
            btnSave.setOnClickListener { updateLoot() }
        }
    }

    private fun updateLoot() {
        if (isValidEntry()) {
            viewModel.updateLoot(
                id = navigationArgs.id,
                name = binding.titNameInput.text.toString(),
                address = binding.titLocationInput.text.toString(),
                inSeason = binding.cbCurrentlyInSeason.isChecked,
                notes = binding.titNotesInput.text.toString()
            )
            findNavController().navigate(R.id.action_addLootFragment_to_lootListFragment)
        }
    }

    private fun addLoot() {
        if (isValidEntry()) {
            viewModel.addLoot(
                binding.titNameInput.text.toString(),
                binding.titLocationInput.text.toString(),
                binding.cbCurrentlyInSeason.isChecked,
                binding.titNotesInput.text.toString()
            )
            findNavController().navigate(R.id.action_addLootFragment_to_lootListFragment)
        }
    }

    private fun deleteLoot(loot: Loot) {
        viewModel.deleteLoot(loot)
        findNavController().navigate(R.id.action_addLootFragment_to_lootListFragment)
    }

    private fun isValidEntry() = viewModel.isValidEntry(
        binding.titNameInput.text.toString(),
        binding.titLocationInput.text.toString()
    )

}