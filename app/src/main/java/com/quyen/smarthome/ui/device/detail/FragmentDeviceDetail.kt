package com.quyen.smarthome.ui.device.detail

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.model.DeviceTime
import com.quyen.smarthome.databinding.FragmentDeviceDetailBinding
import com.quyen.smarthome.service.TimerService
import com.quyen.smarthome.ui.device.detail.adapter.DeviceTimeAdapter
import com.quyen.smarthome.utils.Constant.DEVICE_KEY
import com.quyen.smarthome.utils.getTimeStringFromDouble
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FragmentDeviceDetail : BaseFragment<FragmentDeviceDetailBinding, FragmentDeviceDetailViewModel>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDeviceDetailBinding =
        FragmentDeviceDetailBinding::inflate

    override val viewModel: FragmentDeviceDetailViewModel by activityViewModels()

    private var device: Device? = null
    private val backgroundOn = R.drawable.bg_circle_button_on
    private val backgroundOff = R.drawable.bg_circle_button
    private val timeAdapter by lazy {
        DeviceTimeAdapter(::onItemTimeClick)
    }

    private var time = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))
    }

    override fun initViews() {
        binding.apply {
            recyclerUsedTime.adapter = timeAdapter
            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }
            buttonOnOff.setOnClickListener {
                // turn on/off device
                viewModel.turnDeviceOnOff()
            }
            buttonCounter.setOnClickListener {
                // turn on/off device with timer
                val action =
                    FragmentDeviceDetailDirections.actionFragmentDeviceDetailToDialogCounterTimer(
                        device!!
                    )
                findNavController().navigate(action)
            }
            buttonAlarm.setOnClickListener {
                val action = FragmentDeviceDetailDirections.actionFragmentDeviceDetailToFragmentAlarmDevice(device!!)
                findNavController().navigate(action)
            }
        }
        viewModel.isOn.observe(viewLifecycleOwner, {
            if (it) {
                binding.buttonOnOff.background =
                    activity?.let { activity -> ContextCompat.getDrawable(activity, backgroundOn) }
            } else {
                binding.buttonOnOff.background =
                    activity?.let { activity -> ContextCompat.getDrawable(activity, backgroundOff) }
            }
        })
    }

    override fun initData() {
        device = arguments?.getParcelable(DEVICE_KEY)
        device?.let {
            binding.textToolbarTitle.text = it.device_name
            viewModel.subscribeMqttDevice(it)
            viewModel.getDeviceTimeById(it.device_id)
            viewModel.updateDevice(it)
            if (it.device_info.lowercase().equals("on")) {
                binding.buttonOnOff.background = activity?.let { activity ->
                    ContextCompat.getDrawable(activity, backgroundOn)
                }
            } else {
                binding.buttonOnOff.background = activity?.let { activity ->
                    ContextCompat.getDrawable(activity, backgroundOff)
                }
            }
        }

        viewModel.useTimes.observe(viewLifecycleOwner, {
            timeAdapter.updateData(it.toMutableList())
        })

        viewModel.countTime.observe(viewLifecycleOwner, {
            time = it
            viewModel.startCountDownTimer(time)
        })
    }

    private fun onItemTimeClick(time: DeviceTime) {

    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.BUNDLE_TIME, 0.0)
            try {
                binding.textTimer.text = getTimeStringFromDouble(time)
            } catch (e: Exception) {
                Timber.d(e.message)
            }
        }
    }

}
