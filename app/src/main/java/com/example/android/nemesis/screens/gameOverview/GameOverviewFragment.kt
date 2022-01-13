package com.example.android.nemesis.screens.gameOverview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.android.nemesis.R
import com.example.android.nemesis.database.games.GameDatabase
import com.example.android.nemesis.databinding.FragmentGameOverviewBinding

class GameOverviewFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameOverviewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_overview, container, false)

        // setup the db connection
        val application = requireNotNull(this.activity).application
        val dataSource = GameDatabase.getInstance(application).gameDatabaseDao

        val viewModelFactory = GameOverviewViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(GameOverviewViewModel::class.java)

        // databinding
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // filling the list: game adapter
        val adapter = GameAdapter(GamesListener {
            gameID ->
            Toast.makeText(context, "$gameID", Toast.LENGTH_SHORT).show()
        })
        binding.gameList.adapter = adapter

        // watch the data:
        viewModel.games.observe(viewLifecycleOwner, Observer {

            // don't change the adapters data, use the ListAdapter feature:
            adapter.submitList(it)
        })

        binding.addGameButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.addGameFragment)
        )

        return binding.root
    }
}