package com.example.android.nemesis.screens.games

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.android.nemesis.R
import com.example.android.nemesis.database.games.GameDatabase
import com.example.android.nemesis.databinding.FragmentGamesBinding

class GamesFragment : Fragment() {
    private lateinit var binding: FragmentGamesBinding
    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_games, container, false)

        // Get an instance of the appContext to setup the database
        val appContext = requireNotNull(this.activity).application
        val dataSource = GameDatabase.getInstance(appContext).gameDatabaseDao

        // use a factory to pass the database reference to the viewModel
        val viewModelFactory = GameViewModelFactory(dataSource, appContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java)

        binding.games = viewModel

        // this call allows to automatically update the livedata
        // Meaning: no more resets or whatsoever
        binding.setLifecycleOwner(this)

        binding.addGameButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.addGameFragment)
        )

        return binding.root
    }
}