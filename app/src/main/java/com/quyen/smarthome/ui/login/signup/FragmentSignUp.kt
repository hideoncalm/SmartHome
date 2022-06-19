package com.quyen.smarthome.ui.login.signup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.data.model.User
import com.quyen.smarthome.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FragmentSignUp : BaseFragment<FragmentSignUpBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSignUpBinding =
        FragmentSignUpBinding::inflate
    private val viewModel: FragmentSignupViewModel by viewModels()

    override fun initViews() {
        binding.buttonSignUp.setOnClickListener {
            createUser()
        }
    }

    override fun initData() {
        viewModel.isSignUpSucceed.observe(viewLifecycleOwner, {
            if (it) {
                Snackbar.make(binding.root, "Register Succeed", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(binding.root, "User or password invalid", Snackbar.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun createUser() {
        val email = binding.textPersonEmail.text.toString()
        val password = binding.textPassword.text.toString()
        val confirmPassword = binding.textConfirmPassword.text.toString()
        val userName = binding.textUserName.text.toString()
        Timber.d("LNQ : email : $email, password : $password, confirmPassword : $confirmPassword");
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Snackbar.make(binding.root, "Email or password invalid", Snackbar.LENGTH_SHORT)
                .show()
        } else if (password != confirmPassword) {
            Snackbar.make(binding.root, "password not equals", Snackbar.LENGTH_SHORT)
                .show()
        } else {
            val user = User(email, password, userName)
            viewModel.createUserWithEmail(user)
        }
    }
}
