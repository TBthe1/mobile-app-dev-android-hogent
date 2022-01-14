package com.example.android.nemesis.screens.menu

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.nemesis.MainActivity
import com.example.android.nemesis.R
import com.example.android.nemesis.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)

        setOnClickListeners()

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,
            requireView().findNavController()) ||
                super.onOptionsItemSelected(item)
    }

    private fun setOnClickListeners() {
        binding.menuAboutButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_menuFragment_to_aboutFragment)
        )

        binding.menuAllGamesButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_menuFragment_to_gamesFragment)
        )

        binding.menuLoginButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_menuFragment_to_loginFragment)
        )
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).supportActionBar?.title = getString(R.string.menu)
    }
}