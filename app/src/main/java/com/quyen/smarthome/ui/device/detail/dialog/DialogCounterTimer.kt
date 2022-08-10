package com.quyen.smarthome.ui.device.detail.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.databinding.DialogPickTimeBinding
import com.quyen.smarthome.service.TimerService
import com.quyen.smarthome.service.TimerService.Companion.ACTION_COUNTER
import com.quyen.smarthome.service.TimerService.Companion.BUNDLE_ACTION_ID
import com.quyen.smarthome.service.TimerService.Companion.BUNDLE_DEVICE_ID
import com.quyen.smarthome.service.TimerService.Companion.BUNDLE_DEVICE_MESSAGE
import com.quyen.smarthome.service.TimerService.Companion.BUNDLE_TIME
import com.quyen.smarthome.ui.device.detail.FragmentDeviceDetailViewModel
import com.quyen.smarthome.utils.setWidthPercent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogCounterTimer : DialogFragment() {

    private lateinit var serviceIntent: Intent
    private var device : Device? = null
    private val viewModel : FragmentDeviceDetailViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        serviceIntent = Intent(context, TimerService::class.java)
    }

    lateinit var binding: DialogPickTimeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogPickTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        device = arguments?.getParcelable(DEVICE_KEY)
        setWidthPercent(100)
        val navController = findNavController()
        binding.apply {
            buttonStartTimer.setOnClickListener {
                val time = getTime()
                viewModel.setCountTime(time)
                startTimer()
                navController.popBackStack()
            }
            buttonStopTimer.setOnClickListener {
                resetTimer()
                navController.popBackStack()
            }
        }
    }

    private fun resetTimer() {
        stopTimer()
    }

    private fun getTime(): Double {
        val hour: Int = Integer.parseInt(binding.editHour.text.toString())
        val minute: Int = Integer.parseInt(binding.editMinute.text.toString())
        val second: Int = Integer.parseInt(binding.editSecond.text.toString())
        return (hour * 3600 + minute * 60 + second).toDouble()
    }

    private fun startTimer() {
        val time = getTime()
        var message = "on"
        device?.let {
            message = if (it.device_info.lowercase() == "on") {
                "off"
            } else {
                "on"
            }
        }

        val bundle = Bundle().apply {
            putString(BUNDLE_ACTION_ID, ACTION_COUNTER)
            putString(BUNDLE_DEVICE_ID, device?.device_id)
            putString(BUNDLE_DEVICE_MESSAGE, message)
            putDouble(BUNDLE_TIME, time)
        }

        serviceIntent.putExtras(bundle)
        requireActivity().startService(serviceIntent)
    }

    private fun stopTimer() {
        requireActivity().stopService(serviceIntent)
    }

    companion object {
        private const val DEVICE_KEY = "device"
    }
}
