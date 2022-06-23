package com.quyen.smarthome.ui.login.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentLogin : BaseFragment<FragmentLoginBinding, FragmentLoginViewModel>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding =
        FragmentLoginBinding::inflate


    override val viewModel: FragmentLoginViewModel by viewModels()

    override fun initViews() {
        binding.buttonLogin.setOnClickListener {
            login()
        }
    }

    override fun initData() {
        viewModel.isLoginSucceed.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(R.id.action_fragmentAccount_to_fragmentAddHouse)
            }
        })
    }

    private fun login() {
        val email = binding.textPersonEmail.text.toString()
        val password = binding.textPassword.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            Snackbar.make(binding.root, "User or password invalid", Snackbar.LENGTH_SHORT)
                .show()
        } else {
            viewModel.loginUser(email, password)
        }
    }

}