package com.quyen.smarthome.ui.scenes

import android.view.LayoutInflater
import android.view.ViewGroup
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentScencesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SceneFragment : BaseFragment<FragmentScencesBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentScencesBinding
    = FragmentScencesBinding::inflate

    override fun initViews() {
    }

    override fun initData() {
    }
}