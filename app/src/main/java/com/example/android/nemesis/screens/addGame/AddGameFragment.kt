package com.example.android.nemesis.screens.addGame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.nemesis.MainActivity
import com.example.android.nemesis.R
import com.example.android.nemesis.database.games.GameDatabase
import com.example.android.nemesis.databinding.FragmentAddGameBinding
import kotlinx.android.synthetic.main.fragment_add_game.*
import timber.log.Timber

class AddGameFragment : Fragment() {

    lateinit var binding: FragmentAddGameBinding
    lateinit var viewModel: AddGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_game, container, false)

        // Get an instance of the appContext to setup the database
        val appContext = requireNotNull(this.activity).application
        val dataSource = GameDatabase.getInstance(appContext).gameDatabaseDao

        // use a factory to pass the database reference to the viewModel
        val viewModelFactory = AddGameViewModelFactory(dataSource, appContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddGameViewModel::class.java)

        binding.viewModel = viewModel

        // this call allows to automatically update the livedata
        // Meaning: no more resets or whatsoever
        binding.setLifecycleOwner(this)

        viewModel.saveEvent.observe(viewLifecycleOwner, Observer {
            saveEvent -> if (saveEvent) {
                Timber.i("Koekoek")

                viewModel.saveGame(
                    binding.insertNewGameName.text.toString(),
                    binding.insertNewGameSubtype.text.toString()
                )

                if (insertNewGameName.text.toString() != "" && insertNewGameSubtype.text.toString() != "") {
                    // navigate back to the games screen
                    // view?.findNavController()?.navigate(AddGameFragmentDirections.actionAddGameFragmentToGamesFragment())
                    view?.findNavController()?.navigate(R.id.action_addGameFragment_to_gameOverviewFragment)

                    // Navigation.createNavigateOnClickListener(R.id.action_addGameFragment_to_gameOverviewFragment)
                }

                viewModel.saveEventDone()
            }
        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).supportActionBar?.title = getString(R.string.add_game)
    }
}