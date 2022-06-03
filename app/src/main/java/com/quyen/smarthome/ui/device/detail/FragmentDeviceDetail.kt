package com.quyen.smarthome.ui.device.detail

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.data.model.DeviceTime
import com.quyen.smarthome.databinding.FragmentDeviceDetailBinding
import com.quyen.smarthome.service.TimerService
import com.quyen.smarthome.ui.device.detail.adapter.DeviceTimeAdapter
import com.quyen.smarthome.utils.makeTimeString
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class FragmentDeviceDetail : BaseFragment<FragmentDeviceDetailBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDeviceDetailBinding =
        FragmentDeviceDetailBinding::inflate

    private val viewModel: FragmentDeviceDetailViewModel by viewModels()
    private var device: Device? = null
    private val backgroundOn = R.drawable.bg_circle_button_on
    private val backgroundOff = R.drawable.bg_circle_button
    private val timeAdapter by lazy {
        DeviceTimeAdapter(::onItemTimeClick)
    }

    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        serviceIntent = Intent(context, TimerService::class.java)
        requireActivity().registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))
    }

    override fun initViews() {
        binding.apply {
            recyclerUsedTime.adapter = timeAdapter
            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }
            buttonOnOff.setOnClickListener {
                // call api on/off
                if (viewModel.isOn.value == false) {
                    device?.let { it -> viewModel.turnDeviceOn(it) }
                } else {
                    device?.let { it -> viewModel.turnDeviceOff(it) }
                }
            }
            buttonAlarm.setOnClickListener {
                // call api on/off when exact time
                startStopTimer()
            }
            buttonCounter.setOnClickListener {
                // call api on/off timer
                resetTimer()
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
            viewModel.subscribeMqttDevice(it)
        }
        viewModel.useTimes.observe(viewLifecycleOwner, {
            timeAdapter.updateData(it)
        })
    }

    private fun onItemTimeClick(time: DeviceTime) {

    }

    private fun resetTimer() {
        stopTimer()
        time = 0.0
        binding.textTimer.text = getTimeStringFromDouble(time)
    }

    private fun startStopTimer() {
        if (timerStarted) {
            stopTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        requireActivity().startService(serviceIntent)
        timerStarted = true
    }

    private fun stopTimer() {
        requireActivity().stopService(serviceIntent)
        timerStarted = false
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            binding.textTimer.text = getTimeStringFromDouble(time)
        }
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    companion object {
        private const val DEVICE_KEY = "device"
    }
}
