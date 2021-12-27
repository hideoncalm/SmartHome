package com.quyen.smarthome.ui.me

import android.view.LayoutInflater
import android.view.ViewGroup
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentMeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileFragment : BaseFragment<FragmentMeBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMeBinding
        = FragmentMeBinding::inflate

    override fun initViews() {
    }

    override fun initData() {
    }
}
