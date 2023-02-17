package com.example.loot.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.loot.BaseApplication
import com.example.loot.R
import com.example.loot.databinding.FragmentLootDetailsBinding
import com.example.loot.model.Loot
import com.example.loot.ui.viewmodel.LootViewModel

class LootDetailsFragment : Fragment() {

    private lateinit var loot: Loot

    private val navigationArgs: LootDetailsFragmentArgs by navArgs()

    private val viewModel: LootViewModel by activityViewModels {
        LootViewModel.LootViewModelFactory((activity?.application as BaseApplication).database.lootDao())
    }

    private var _binding: FragmentLootDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLootDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
         viewModel.retrieveLoot(id).observe(viewLifecycleOwner) {
             it.let {
                 loot = it
             }
         }
        bind()
    }

    private fun bind() {
        binding.apply {
            tvName.text = loot.name
            tvAddress.text = loot.address
            tvNotes.text = loot.notes
            if (loot.inSeason) {
                tvSeason.text = getString(R.string.currently_in_season)
            } else tvSeason.text = getString(R.string.out_of_season)
            fabEditLoot.setOnClickListener {
                val action = LootDetailsFragmentDirections.actionLootDetailsFragmentToAddLootFragment(loot.id)
                findNavController().navigate(action)
            }
        }
    }

}