package com.quyen.smarthome.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.base.BaseViewPagerAdapter
import com.quyen.smarthome.databinding.FragmentHomeBinding
import com.quyen.smarthome.ui.device.listdevices.ListDevicesFragment
import com.quyen.smarthome.ui.room.listrooms.FragmentListItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    override fun initViews() {
    }

    override fun initData() {
    }
}
