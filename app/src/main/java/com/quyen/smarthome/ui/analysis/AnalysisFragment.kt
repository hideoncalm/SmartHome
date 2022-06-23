package com.quyen.smarthome.ui.analysis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentAnalysisBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalysisFragment : BaseFragment<FragmentAnalysisBinding, AnalysisFragmentViewModel>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAnalysisBinding
        = FragmentAnalysisBinding::inflate
    override val viewModel: AnalysisFragmentViewModel by viewModels()

    override fun initViews() {
    }

    override fun initData() {
    }

}
