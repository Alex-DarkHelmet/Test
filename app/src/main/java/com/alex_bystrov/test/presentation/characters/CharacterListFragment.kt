package com.alex_bystrov.test.presentation.characters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alex_bystrov.test.R
import com.alex_bystrov.test.databinding.CharactersListFragmentBinding
import com.alex_bystrov.test.presentation.auth.AuthViewModel
import com.alex_bystrov.test.presentation.auth.SignInFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private var _binding: CharactersListFragmentBinding? = null
    private val binding: CharactersListFragmentBinding
        get() = _binding ?: throw RuntimeException("CharactersListFragmentBinding = null")

    private val viewModel by viewModels<CharactersViewModel>()
    private val authViewModel by viewModels<AuthViewModel>()

    private lateinit var characterAdapter: CharactersListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CharactersListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

//        viewModel.getCharactersFromPage(parseArguments())
//        Log.i("Arguments", "Page - ${parseArguments()}")
        viewModel.getCharacters()

        getCharactersFromVM()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArguments(): Int {
        val args = requireArguments()
        return if (args.containsKey(PAGE_KEY)) {
            args.getInt(PAGE_KEY)
        } else {
            0
        }
    }


    private fun setupRecyclerView() {
        characterAdapter = CharactersListAdapter()
        binding.rvCharactersList.adapter = characterAdapter
    }

    private fun getCharactersFromVM() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.characters.collect { characters ->
                    characterAdapter.addCharacters(characters)
                    Log.i("CharacterList", "${characters.size}")

                    if (characters.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            binding.progressLoading.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun launchSignInScreen() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, SignInFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    companion object {
        private const val PAGE_KEY = "user"

        fun newInstance(page: Int): CharacterListFragment {
            return CharacterListFragment().apply {
                arguments = Bundle().apply {
                    putInt(PAGE_KEY, page)
                }
            }
        }
    }
}