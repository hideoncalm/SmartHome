package com.quyen.smarthome.ui.device.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.databinding.FragmentSettingDeviceBinding
import com.quyen.smarthome.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentDeviceSetting : BaseFragment<FragmentSettingDeviceBinding, FragmentSettingDeviceViewModel>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingDeviceBinding
        = FragmentSettingDeviceBinding::inflate

    override val viewModel: FragmentSettingDeviceViewModel by viewModels()
    private var device : Device? = null

    override fun initViews() {
        device = arguments?.getParcelable(Constant.DEVICE_KEY)
        device?.let {
            binding.textToolbarTitle.text = it.device_name
        }
    }

    override fun initData() {
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonCreateHome.setOnClickListener {
            device?.let {
                it.device_name = binding.textHomeName.text.toString()
                viewModel.updateDevice(it)
                findNavController().popBackStack()
            }
        }
    }
}