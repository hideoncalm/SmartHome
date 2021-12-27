package com.quyen.smarthome.ui.analysis

import android.view.LayoutInflater
import android.view.ViewGroup
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentAnalysisBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalysisFragment : BaseFragment<FragmentAnalysisBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAnalysisBinding
        = FragmentAnalysisBinding::inflate

    override fun initViews() {
    }

    override fun initData() {
    }
}
