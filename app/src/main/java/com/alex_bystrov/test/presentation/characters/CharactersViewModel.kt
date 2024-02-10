package com.alex_bystrov.test.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex_bystrov.test.data.remote.characters.CharactersRepositoryImpl
import com.alex_bystrov.test.model.Character
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {
    private val repository = CharactersRepositoryImpl()

    val characters: StateFlow<List<Character>> get() =  repository.characters

    fun getCharacters() {
        viewModelScope.launch {
            repository.fetchCharacters()
        }
    }

    fun getCharactersFromPage(page: Int) {
        viewModelScope.launch {
            repository.fetchCharactersFromPage(page = page)
        }
    }
}