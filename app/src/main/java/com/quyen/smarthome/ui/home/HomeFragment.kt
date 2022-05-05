package com.quyen.smarthome.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        = FragmentHomeBinding::inflate
    private val titles = listOf("Living room", "Bed Room", "Kitchen")
    override fun initViews() {
        binding.buttonAddDevice.setOnClickListener {
        }
    }

    override fun initData() {
    }
}
