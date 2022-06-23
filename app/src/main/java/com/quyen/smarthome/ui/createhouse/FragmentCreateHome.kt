package com.quyen.smarthome.ui.createhouse

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.data.model.Home
import com.quyen.smarthome.databinding.FragmentAddHomeBinding
import com.quyen.smarthome.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentCreateHome : BaseFragment<FragmentAddHomeBinding, FragmentCreateHomeViewModel>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAddHomeBinding =
        FragmentAddHomeBinding::inflate
    override val viewModel: FragmentCreateHomeViewModel by viewModels()

    @Inject
    lateinit var sharePre: SharedPreferences

    override fun initViews() {
        binding.buttonCreateHome.setOnClickListener {
            val userID = sharePre.getString(Constant.SHARED_USER_KEY, "")
            val homeN = binding.textHomeName.text.toString()
            val homeAdd = binding.textHomeAddress.text.toString()
            val home = Home().apply {
                home_name = homeN
                home_address = homeAdd
                home_user_id = userID!!
            }
            viewModel.createHome(home)
            findNavController().popBackStack()
        }
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initData() {
    }
}
