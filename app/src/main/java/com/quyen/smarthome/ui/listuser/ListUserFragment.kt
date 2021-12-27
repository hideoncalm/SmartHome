package com.quyen.smarthome.ui.listuser

import android.view.LayoutInflater
import android.view.ViewGroup
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListUserFragment : BaseFragment<FragmentUserBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentUserBinding =
        FragmentUserBinding::inflate

    override fun initViews() {
    }

    override fun initData() {
    }
}
