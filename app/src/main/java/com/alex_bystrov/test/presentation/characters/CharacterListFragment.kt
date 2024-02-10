package com.alex_bystrov.test.presentation.characters

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.alex_bystrov.test.R
import com.alex_bystrov.test.databinding.CharactersListFragmentBinding
import com.alex_bystrov.test.presentation.auth.AuthViewModel
import com.alex_bystrov.test.presentation.auth.SignInFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterListFragment : Fragment() {

    private var _binding: CharactersListFragmentBinding? = null
    private val binding: CharactersListFragmentBinding
        get() = _binding ?: throw RuntimeException("CharactersListFragmentBinding = null")

    private val viewModel = CharactersViewModel()
    private val authViewModel = AuthViewModel()

    private lateinit var characterAdapter: CharactersListAdapter

    private var currentAnimator: Animator? = null

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

//        viewModel.getCharacters()
        viewModel.getCharactersFromPage(parseArguments())
        Log.i("Arguments", "Page - ${parseArguments()}")

        getCharactersFromVM()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_user -> {
                authViewModel.singOut()
                launchSignInScreen()
                true
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
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
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.characters.collect { characters ->
                characterAdapter.submitList(characters)
                Log.i("CharacterList", "${viewModel.characters.value}")
                launch(Dispatchers.Main) {
                    if (viewModel.characters.value.isNotEmpty()) {
                        binding.progressLoading.visibility = View.GONE
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