package com.quyen.smarthome.ui.roomdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentRoomDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomDetailFragment : BaseFragment<FragmentRoomDetailBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRoomDetailBinding =
        FragmentRoomDetailBinding::inflate

    override fun initViews() {
    }

    override fun initData() {
    }
}
