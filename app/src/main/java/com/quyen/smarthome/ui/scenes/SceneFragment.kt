package com.quyen.smarthome.ui.scenes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentScencesBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SceneFragment : BaseFragment<FragmentScencesBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentScencesBinding
    = FragmentScencesBinding::inflate
    private val viewModel : SceneViewModel by viewModels()

    override fun initViews() {

        viewModel.scenes.observe(viewLifecycleOwner, {
            Timber.d("LNQ : ${it.size}")
        })
    }

    override fun initData() {
    }
}
