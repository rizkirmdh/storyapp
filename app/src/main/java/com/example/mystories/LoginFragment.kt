package com.example.mystories

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.mystories.MainActivity.Companion.TOKEN
import com.example.mystories.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var loginJob: Job = Job()
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmailLogin.text.toString().trim()
            val password = binding.etPasswordLogin.text.toString()

            lifecycleScope.launchWhenResumed {
                if (loginJob.isActive) loginJob.cancel()

                loginJob = launch {
                    viewModel.accountLogin(email, password).collect { result ->
                        result.onSuccess {
                            it.loginResult?.token?.let { token ->
                                viewModel.saveToken(token)
                                Intent(requireContext(), MainActivity::class.java).also { intent ->
                                    intent.putExtra(TOKEN, token)
                                    startActivity(intent)
                                    requireActivity().finish()
                                }
                            }

                            Toast.makeText(
                                requireContext(),
                                getString(R.string.login_success),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        result.onFailure {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.login_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        binding.btnRegister.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registerFragment)
            )
    }
}