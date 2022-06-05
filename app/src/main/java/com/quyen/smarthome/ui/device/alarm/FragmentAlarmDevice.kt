package com.quyen.smarthome.ui.device.alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.databinding.FragmentSelectAlarmBinding
import com.quyen.smarthome.utils.Constant

class FragmentAlarmDevice : BaseFragment<FragmentSelectAlarmBinding>() {

    private var device: Device? = null
    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSelectAlarmBinding =
        FragmentSelectAlarmBinding::inflate

    override fun initViews() {
        binding.apply {
            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }
            buttonStartTimer.setOnClickListener {
                findNavController().popBackStack()
            }
            buttonStopTimer.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun initData() {
        device = arguments?.getParcelable(Constant.DEVICE_KEY)
        binding.textToolbarTitle.text = device!!.device_name
    }
}