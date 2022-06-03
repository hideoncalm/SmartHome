package com.quyen.smarthome.ui.main

import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseActivity
import com.quyen.smarthome.data.source.remote.UserRemoteDataSource
import com.quyen.smarthome.databinding.ActivityMainBinding
import com.quyen.smarthome.service.disconnectMqtt
import com.quyen.smarthome.service.mqttClientConnect
import dagger.hilt.android.AndroidEntryPoint
import org.eclipse.paho.android.service.MqttAndroidClient
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @Inject
    lateinit var userData: UserRemoteDataSource

    @Inject
    lateinit var mqttClient: MqttAndroidClient

    override fun initViews() {
        setUpBottomNavigation()
    }

    override fun initData() {
        mqttClientConnect(mqttClient)
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
            selectedItemId = R.id.homeFragment
            setOnItemReselectedListener { }
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
