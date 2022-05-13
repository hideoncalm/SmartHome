package com.quyen.smarthome.ui.room.listrooms

import android.view.LayoutInflater
import android.view.ViewGroup
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentRoomsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentListItem : BaseFragment<FragmentRoomsBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRoomsBinding =
        FragmentRoomsBinding::inflate

    override fun initViews() {
    }

    override fun initData() {
    }
}
