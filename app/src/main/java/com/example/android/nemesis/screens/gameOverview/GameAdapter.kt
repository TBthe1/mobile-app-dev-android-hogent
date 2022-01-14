package com.example.android.nemesis.screens.gameOverview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.nemesis.databinding.GameListItemBinding
import com.example.android.nemesis.domain.Game

class GameAdapter(val clickListener: GamesListener) : ListAdapter<Game, ViewHolder>(GameDiffCallback()) {

    // fill up the item you need (e.g. set texts and images)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}

class ViewHolder(val binding: GameListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: GamesListener, item: Game) {
        binding.gameNameTextview.text = item.gameName
        binding.gameDescTextview.text = item.gameSubtype

        binding.game = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    // this way the viewHolder knows how to inflate.
    // better than having this in the adapter.
    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = GameListItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}

class GameDiffCallback : DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.gameId == newItem.gameId
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
        // works perfectly because it's a dataclass.
    }
}

class GamesListener(val clickListener: (gameID: Long) -> Unit) {
    fun onClick(game: Game) = clickListener(game.gameId)
}