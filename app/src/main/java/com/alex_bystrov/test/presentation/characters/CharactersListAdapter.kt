package com.alex_bystrov.test.presentation.characters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.alex_bystrov.test.R
import com.alex_bystrov.test.databinding.CardCharacterBinding
import com.alex_bystrov.test.domain.model.Character
import kotlinx.coroutines.flow.MutableStateFlow

class CharactersListAdapter: ListAdapter<Character, CharactersListAdapter.CharacterViewHolder>(
    CharactersDiffUtilCallBack()
) {

    private var counter = 0
    private val charaacters: MutableList<Character> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CardCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        counter++
        Log.i("CharacterAdapter", "$counter")
        return CharacterViewHolder(binding)
    }

    fun addCharacters(charecterList: List<Character>) {
        charaacters.addAll(charecterList)
        submitList(charaacters.toList())
    }


    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)

        holder.binding.tvNameCharacter.text = character.name
        holder.binding.tvStatus.text = character.status
        holder.binding.tvSpecies.text = character.species
        holder.binding.ivImageCharacter.load(character.image) {
            transformations(RoundedCornersTransformation(16f))
        }

        holder.itemView.startAnimation(
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.test_animation)
        )

        when (character.status) {
            ALIVE_STATUS -> holder.binding.ivStatusImage.setImageResource(R.drawable.alive_status_24)
            DEAD_STATUS -> holder.binding.ivStatusImage.setImageResource(R.drawable.dead_status_24)
            UNKNOWN_STATUS -> holder.binding.ivStatusImage.setImageDrawable(null)
        }
    }

    private fun zoomImage() {
        TODO("Write zoom animation to Image of Character")
    }

    companion object {
        private const val ALIVE_STATUS = "Alive"
        private const val DEAD_STATUS = "Dead"
        private const val UNKNOWN_STATUS = "unknown"
    }

    class CharacterViewHolder(val binding: CardCharacterBinding): RecyclerView.ViewHolder(binding.root)
}