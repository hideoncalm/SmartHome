package com.quyen.smarthome.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseActivity
import com.quyen.smarthome.data.source.remote.UserRemoteDataSource
import com.quyen.smarthome.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @Inject
    lateinit var userData : UserRemoteDataSource

    override fun initViews() {
        setUpBottomNavigation()
    }

    override fun initData() {
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

    companion object {
        private val mainFragment = listOf(
            R.id.homeFragment,
            R.id.listDevicesFragment,
            R.id.sceneFragment,
            R.id.fragmentListItem
        )
    }
}
