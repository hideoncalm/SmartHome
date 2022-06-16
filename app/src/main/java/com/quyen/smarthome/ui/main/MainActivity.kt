package com.quyen.smarthome.ui.main

import android.content.Intent
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseActivity
import com.quyen.smarthome.data.source.remote.UserRemoteDataSource
import com.quyen.smarthome.databinding.ActivityMainBinding
import com.quyen.smarthome.service.disconnectMqtt
import com.quyen.smarthome.service.mqttClientConnect
import dagger.hilt.android.AndroidEntryPoint
import org.eclipse.paho.android.service.MqttAndroidClient
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @Inject
    lateinit var userData: UserRemoteDataSource

    @Inject
    lateinit var mqttClient: MqttAndroidClient

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun initViews() {
        setUpBottomNavigation()
    }

    override fun initData() {
        mqttClientConnect(mqttClient,
            {
                Timber.d("LNQ : Connected Success")
            }, { _, _ ->
                Timber.d("LNQ : Connected Failed")
            })
    }

    private fun setUpBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentNavHost) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigation.isVisible = destination.id in mainFragment
        }
        binding.bottomNavigation.apply {
            setupWithNavController(navController)
            setOnItemReselectedListener { }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disconnectMqtt(mqttClient)
    }

    companion object {
        private val mainFragment = listOf(
            R.id.homeFragment,
            R.id.listDevicesFragment,
            R.id.sceneFragment,
            R.id.fragmentListItem
        )
    }
}
