package com.quyen.smarthome.ui.login.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.quyen.smarthome.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.net.UnknownServiceException
import javax.inject.Inject

@HiltViewModel
class FragmentSignupViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _isSignUpSucceed : MutableLiveData<Boolean> = MutableLiveData()
    val isSignUpSucceed : LiveData<Boolean>
        get() = _isSignUpSucceed

    fun createUserWithEmail(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _isSignUpSucceed.postValue(true)
                val user = User()
            }
        }
    }
}