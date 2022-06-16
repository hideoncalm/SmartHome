package com.quyen.smarthome.ui.login.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer

@HiltViewModel
class FragmentLoginViewModel @Inject constructor(
    private var firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _isLoginSucceed: MutableLiveData<Boolean> = MutableLiveData()
    val isLoginSucceed: LiveData<Boolean>
        get() = _isLoginSucceed

    fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                // login success
                _isLoginSucceed.postValue(true)
            } else {
                // login failed
                _isLoginSucceed.postValue(false)
            }
        }
    }
}