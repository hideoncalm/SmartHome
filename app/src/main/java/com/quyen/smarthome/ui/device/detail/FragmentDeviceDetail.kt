package com.quyen.smarthome.ui.device.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentDeviceDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentDeviceDetail : BaseFragment<FragmentDeviceDetailBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDeviceDetailBinding =
        FragmentDeviceDetailBinding::inflate

    override fun initViews() {
    }

    override fun initData() {
    }
}
