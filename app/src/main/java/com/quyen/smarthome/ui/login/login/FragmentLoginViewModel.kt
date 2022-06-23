package com.quyen.smarthome.ui.login.login

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.quyen.smarthome.base.BaseViewModel
import com.quyen.smarthome.data.source.remote.UserRemoteDataSource
import com.quyen.smarthome.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentLoginViewModel @Inject constructor(
    private var firebaseAuth: FirebaseAuth,
    private val userRepo: UserRemoteDataSource,
    private var sharePre: SharedPreferences
) : BaseViewModel() {

    private val _isLoginSucceed: MutableLiveData<Boolean> = MutableLiveData()
    val isLoginSucceed: LiveData<Boolean>
        get() = _isLoginSucceed


    fun loginUser(email: String, password: String) {
        showLoading()
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                // login success
                viewModelScope.launch {
                    val user = async { userRepo.getUserById(email) }
                    user.await().let { u ->
                        sharePre.edit().apply {
                            putString(Constant.SHARED_USER_KEY, u?.user_email)
                            putString(Constant.SHARED_PASSWORD_KEY, u?.user_password)
                        }.apply()
                        _isLoginSucceed.postValue(true)
                        setMessage("Login Succeed")
                    }
                }
                hideLoading()
            } else {
                // login failed
                _isLoginSucceed.postValue(false)
                hideLoading()
                setMessage("User or password invalid")
            }
        }
    }
}
