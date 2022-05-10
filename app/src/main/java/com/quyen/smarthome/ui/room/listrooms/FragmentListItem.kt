package com.quyen.smarthome.ui.room.listrooms

import android.view.LayoutInflater
import android.view.ViewGroup
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentPagerListItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentListItem : BaseFragment<FragmentPagerListItemBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPagerListItemBinding =
        FragmentPagerListItemBinding::inflate
    override fun initViews() {
    }

    override fun initData() {
    }
}
