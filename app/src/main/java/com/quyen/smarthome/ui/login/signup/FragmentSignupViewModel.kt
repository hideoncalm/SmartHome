package com.quyen.smarthome.ui.login.signup

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.quyen.smarthome.base.BaseViewModel
import com.quyen.smarthome.data.model.User
import com.quyen.smarthome.data.source.remote.UserRemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentSignupViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userRepo: UserRemoteDataSource
) : BaseViewModel() {

    fun createUserWithEmail(user: User) {
        showLoading()
        firebaseAuth.createUserWithEmailAndPassword(user.user_email, user.user_password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    setMessage("Register Succeed")
                    viewModelScope.launch(Dispatchers.IO) {
                        userRepo.insertUser(user)
                    }
                } else {
                    setMessage("User or password invalid")
                }
                hideLoading()
            }
    }
}
