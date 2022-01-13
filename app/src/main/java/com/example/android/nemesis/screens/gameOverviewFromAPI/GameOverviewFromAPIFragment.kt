package com.example.android.nemesis.screens.gameOverviewFromAPI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.nemesis.R
import com.example.android.nemesis.databinding.FragmentGameOverviewFromApiBinding

class GameOverviewFromAPIFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameOverviewFromApiBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_overview_from_api, container, false)
        val viewModel = FromAPIViewModel()

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }
}