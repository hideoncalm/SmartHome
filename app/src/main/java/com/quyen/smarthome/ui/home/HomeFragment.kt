package com.quyen.smarthome.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.base.BaseViewPagerAdapter
import com.quyen.smarthome.databinding.FragmentHomeBinding
import com.quyen.smarthome.ui.room.listrooms.FragmentListItem
import com.quyen.smarthome.ui.device.listdevices.ListNewDevicesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate

    private val titles = listOf("New Devices", "Rooms")
    private var fragments = listOf<Fragment>(ListNewDevicesFragment(), FragmentListItem())

    private val homeAdapter by lazy {
        BaseViewPagerAdapter(fragments, titles, childFragmentManager)
    }

    override fun initViews() {
        binding.apply {
            viewPager.adapter = homeAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    override fun initData() {
    }
}
