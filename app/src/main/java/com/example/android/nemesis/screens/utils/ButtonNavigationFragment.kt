package com.example.android.nemesis.screens.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.android.nemesis.R
import com.example.android.nemesis.databinding.FragmentButtonNavigationBinding

class ButtonNavigationFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var binding: FragmentButtonNavigationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_button_navigation, container, false)
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_button_navigation, container, false)

        binding.button2.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_menuFragment_to_gamesFragment))
        return binding.root
    }
}