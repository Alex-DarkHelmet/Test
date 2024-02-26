package com.alex_bystrov.test.presentation.characters

import androidx.recyclerview.widget.DiffUtil
import com.alex_bystrov.test.domain.model.Character

class CharactersDiffUtilCallBack: DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }
}