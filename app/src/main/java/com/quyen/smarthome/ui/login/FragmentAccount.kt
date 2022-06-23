package com.quyen.smarthome.ui.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.base.BaseViewPagerAdapter
import com.quyen.smarthome.databinding.FragmentAccountBinding
import com.quyen.smarthome.ui.login.login.FragmentLogin
import com.quyen.smarthome.ui.login.signup.FragmentSignUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAccount : BaseFragment<FragmentAccountBinding, FragmentAccountViewModel>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAccountBinding =
        FragmentAccountBinding::inflate

    override val viewModel: FragmentAccountViewModel by viewModels()

    private val accountAdapter by lazy {
        BaseViewPagerAdapter(
            listOf(FragmentLogin(), FragmentSignUp()),
            listOf("Login", "Sign up"),
            childFragmentManager
        )
    }

    override fun initViews() {
        binding.apply {
            viewPager.adapter = accountAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    override fun initData() {
    }


}