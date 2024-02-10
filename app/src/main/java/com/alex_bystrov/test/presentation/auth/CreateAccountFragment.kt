package com.alex_bystrov.test.presentation.auth

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.alex_bystrov.test.R
import com.alex_bystrov.test.data.remote.auth.AuthAnswer
import com.alex_bystrov.test.databinding.CreateAcctountFragmentBinding
import com.alex_bystrov.test.presentation.characters.CharacterListFragment
import kotlinx.coroutines.launch


class CreateAccountFragment : Fragment() {
    private var _binding: CreateAcctountFragmentBinding? = null
    private val binding: CreateAcctountFragmentBinding
        get() = _binding ?: throw RuntimeException("CreateAccountFragment == null")

    private val viewModel = AuthViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CreateAcctountFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        with(binding) {
            btnCreateAccount.setOnClickListener {

                viewModel.createAccountResponse(
                    email = tvInputEmail.text.toString(),
                    password = tvInputPassword.text.toString(),
                )

                changeTextColor()

                observeAuthState()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun observeAuthState() {
        lifecycleScope.launch {
            viewModel.authState.collect { state ->
                when (state) {
                    is AuthAnswer.Error -> {
                        Toast.makeText(
                            context,
                            "Error! Code -${state.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    AuthAnswer.Loading -> {
                        animationSignUpBtn()
                    }

                    is AuthAnswer.Succeed -> {
//                        launchCharactersListFragment()
                    }

                    else -> {}
                }
            }
        }
    }

    private fun animationSignUpBtn() {
        binding.btnCreateAccount.animate().apply {
            duration = ANIMATION_TIME
            rotationXBy(SPIN)
        }.start()
    }

    private fun changeTextColor() {
        binding.tvInputPassword.setTextColor(Color.RED)
        binding.tvConfirmPassword.text = null
        Toast.makeText(context, "Incorrect email or password", Toast.LENGTH_SHORT).show()
    }

    private fun validEmailAndPassword(): Boolean {
        return binding.tvInputEmail.text.toString().contains('@')
                &&
                binding.tvInputEmail.text.toString().contains('.')
                &&
                binding.tvInputPassword.text.toString().length > 6
    }

//    private fun launchCharactersListFragment() {
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.container, CharacterListFragment.newInstance())
//            .commit()
//    }

    companion object {
        private const val ANIMATION_TIME = 1000L
        private const val SPIN = 360f

        fun newInstance(): CreateAccountFragment {
            return CreateAccountFragment()
        }
    }
}