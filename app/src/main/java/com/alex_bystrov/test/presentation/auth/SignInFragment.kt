package com.alex_bystrov.test.presentation.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alex_bystrov.test.R
import com.alex_bystrov.test.data.remote.auth.AuthAnswer
import com.alex_bystrov.test.databinding.SingInFragmentBinding
import com.alex_bystrov.test.presentation.characters.CharacterListFragment
import kotlinx.coroutines.launch
import kotlin.math.abs

class SignInFragment : Fragment() {
    private var _binding: SingInFragmentBinding? = null
    private val binding: SingInFragmentBinding
        get() = _binding ?: throw RuntimeException("LoginFragmentBinding = null")

    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SingInFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        launchCharactersFragment("1")

        collectAuthState()

        binding.tvCreateAccount.setOnClickListener {
            launchCreateAccountFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun collectAuthState() {
        lifecycleScope.launch {
            viewModel.authState.collect { state ->
                Log.e("AuthState", "${viewModel.authState.value}")
                when (state) {
                    is AuthAnswer.Error -> {
                        Toast.makeText(
                            context,
                            "${viewModel.authState.value}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is AuthAnswer.Succeed -> {
                        val succeedAnswer = viewModel.authState.value as AuthAnswer.Succeed
                        launchCharactersFragment(succeedAnswer.userId)
                    }

                    AuthAnswer.Init -> {
                        binding.btnSignIn.setOnClickListener {
                            if (validEmailAndPassword()) {
                                viewModel.signInResponse(
                                    email = binding.tvInputEmail.text.toString(),
                                    password = binding.tvInputPassword.text.toString()
                                )
                                loadingAnimation()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Incorrect email or password",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun loadingAnimation() {
        binding.btnSignIn.animate().apply {
            duration = ANIMATION_TIME
            rotationYBy(SPIN)
        }.start()
    }

    private fun launchCharactersFragment(userUid: String) {
        val page = getNumberOfCharactersPage(userUid)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, CharacterListFragment.newInstance(page = page))
            .addToBackStack(null)
            .commit()
    }

    private fun getNumberOfCharactersPage(userUid: String): Int {
        return abs(userUid.hashCode() % MAX_PAGES) + 1
    }

    private fun launchCreateAccountFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, CreateAccountFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    private fun validEmailAndPassword(): Boolean {
        return binding.tvInputEmail.text.toString().contains('@')
                &&
                binding.tvInputEmail.text.toString().contains('.')
                &&
                binding.tvInputPassword.text.toString().length > 6
    }

    companion object {
        private const val ANIMATION_TIME = 1000L
        private const val SPIN = 360f
        private const val MAX_PAGES = 40

        fun newInstance(): SignInFragment = SignInFragment()
    }
}