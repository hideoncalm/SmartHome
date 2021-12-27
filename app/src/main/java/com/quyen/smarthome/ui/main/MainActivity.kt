package com.quyen.smarthome.ui.main

import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.database.FirebaseDatabase
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseActivity
import com.quyen.smarthome.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun initViews() {
        setUpBottomNavigation()
    }

    override fun initData() {
        val database : FirebaseDatabase = FirebaseDatabase.getInstance("https://iot-project-ebf71-default-rtdb.asia-southeast1.firebasedatabase.app")
        val myRef = database.getReference("name")
        myRef.setValue("Le Ngoc Quyen dep trai vo dich")
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
            selectedItemId = R.id.analysisFragment
            setOnItemReselectedListener { }
        }
    }

    companion object {
        private val mainFragment = listOf(
            R.id.analysisFragment,
            R.id.homeFragment,
            R.id.myProfileFragment
        )
    }
}
