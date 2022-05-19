package com.quyen.smarthome.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    override fun initViews() {
        binding.buttonAddDevice.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_fragmentAddDevice)
        }
    }

    override fun initData() {
    }
}
