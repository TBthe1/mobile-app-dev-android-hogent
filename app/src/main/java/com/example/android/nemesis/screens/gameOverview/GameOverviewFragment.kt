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
import com.google.android.material.chip.Chip

class GameOverviewFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var binding: FragmentGameOverviewBinding
    lateinit var viewModel: GameOverviewViewModel
    lateinit var adapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_overview, container, false)

        // setup the db connection
        val application = requireNotNull(this.activity).application
        val dataSource = GameDatabase.getInstance(application).gameDatabaseDao

        // filling the list: joke adapter
        adapter = GameAdapter(GamesListener {
                gameID ->
            Toast.makeText(context, "$gameID", Toast.LENGTH_SHORT).show()
        })
        binding.gameList.adapter = adapter

        // viewmodel
        val viewModelFactory = GameOverviewViewModelFactory(dataSource, application, adapter)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GameOverviewViewModel::class.java)

        // databinding
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // list changed
        viewModel.games.observe(viewLifecycleOwner, Observer {

            // don't change the adapters data, use the ListAdapter feature:
            adapter.submitList(it)
        })

        binding.addGameButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.addGameFragment)
        )

        createChips(listOf("<10", "10-20", ">20"))
        return binding.root
    }

    private fun createChips(data: List<String>) {
        // take care: the example in the movies has dynamic chips
        // these are hardcoded.
        val chipGroup = binding.chipList
        val inflator = LayoutInflater.from(chipGroup.context)

        val children = data.map {
                text ->
            val chip = inflator.inflate(R.layout.chip, chipGroup, false) as Chip
            chip.text = text
            chip.tag = text
            chip.setOnCheckedChangeListener {
                    button, isChecked ->
                viewModel.filterChip(button.tag as String, isChecked)
            }
            chip
        }

        // remove existing children
        chipGroup.removeAllViews()

        // add the new children
        for (chip in children) {
            chipGroup.addView(chip)
        }
    }
}