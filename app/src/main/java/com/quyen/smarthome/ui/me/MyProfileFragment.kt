package com.quyen.smarthome.ui.me

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.databinding.FragmentMeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileFragment : BaseFragment<FragmentMeBinding, MyProfileViewModel>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMeBinding
        = FragmentMeBinding::inflate
    override val viewModel: MyProfileViewModel by viewModels()

    override fun initViews() {
    }

    override fun initData() {
    }

}
