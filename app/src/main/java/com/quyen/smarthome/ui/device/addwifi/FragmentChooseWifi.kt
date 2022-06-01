package com.quyen.smarthome.ui.device.addwifi

import android.view.LayoutInflater
import android.view.ViewGroup
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentChooseWifiBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentChooseWifi : BaseFragment<FragmentChooseWifiBinding>(){

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentChooseWifiBinding
        = FragmentChooseWifiBinding::inflate

    override fun initViews() {
    }

    override fun initData() {
    }
}
