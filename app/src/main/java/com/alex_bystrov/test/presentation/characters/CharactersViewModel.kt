package com.alex_bystrov.test.presentation.characters

import androidx.lifecycle.ViewModel
import com.alex_bystrov.test.common.AppDispatchers
import com.alex_bystrov.test.data.remote.characters.CharactersRepository
import com.alex_bystrov.test.domain.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val dispatcher: AppDispatchers,
    private val repository: CharactersRepository
) : ViewModel() {

    private val _characters: MutableSharedFlow<List<Character>> = MutableSharedFlow()
    val characters: Flow<List<Character>> = _characters

    fun getCharacters() {
        CoroutineScope(Job() + dispatcher.io).launch {
            val data = repository.getCharacters()
            data.collect {
                _characters.emit(it)
            }
        }
//        if (_characters.value.isNotEmpty()) {
//            job.cancel()
//        }
    }

//    fun getCharactersFromPage(page: Int) {
//        CoroutineScope(Job() + Dispatchers.IO).launch {
//            val response = repository.getCharactersByPage(page = page)
//            _characters.emit(response)
//        }
//    }
}