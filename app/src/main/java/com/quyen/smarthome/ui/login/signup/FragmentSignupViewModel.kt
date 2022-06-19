package com.quyen.smarthome.ui.login.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.quyen.smarthome.data.model.User
import com.quyen.smarthome.data.source.remote.UserRemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentSignupViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userRepo : UserRemoteDataSource
) : ViewModel() {

    private val _isSignUpSucceed : MutableLiveData<Boolean> = MutableLiveData()
    val isSignUpSucceed : LiveData<Boolean>
        get() = _isSignUpSucceed

    fun createUserWithEmail(user : User) {
        firebaseAuth.createUserWithEmailAndPassword(user.user_email, user.user_password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _isSignUpSucceed.postValue(true)
                    viewModelScope.launch(Dispatchers.IO) {
                        userRepo.insertUser(user)
                    }
                }
            }
    }
}
