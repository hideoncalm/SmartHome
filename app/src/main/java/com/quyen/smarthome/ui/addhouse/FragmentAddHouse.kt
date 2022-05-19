package com.quyen.smarthome.ui.addhouse

import android.view.LayoutInflater
import android.view.ViewGroup
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentHouseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAddHouse : BaseFragment<FragmentHouseBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHouseBinding =
        FragmentHouseBinding::inflate

    override fun initViews() {
    }

    override fun initData() {
    }
}
