package com.quyen.smarthome.ui.device.detail.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.databinding.DialogPickTimeBinding
import com.quyen.smarthome.service.TimerService
import com.quyen.smarthome.service.TimerService.Companion.BUNDLE_DEVICE_ID
import com.quyen.smarthome.service.TimerService.Companion.BUNDLE_DEVICE_MESSAGE
import com.quyen.smarthome.service.TimerService.Companion.BUNDLE_TIME
import com.quyen.smarthome.ui.device.detail.FragmentDeviceDetail
import com.quyen.smarthome.utils.setWidthPercent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogCounterTimer : DialogFragment() {

    private lateinit var serviceIntent: Intent
    private var time = 0.0
    private var device : Device? = null

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
        binding.apply {
            buttonStartTimer.setOnClickListener {
                startTimer()
                findNavController().popBackStack()
            }
            buttonStopTimer.setOnClickListener {
                resetTimer()
                findNavController().popBackStack()
            }
        }
    }

    private fun resetTimer() {
        stopTimer()
    }

    private fun startTimer() {
        val hour: Int = Integer.parseInt(binding.editHour.text.toString())
        val minute: Int = Integer.parseInt(binding.editMinute.text.toString())
        val second: Int = Integer.parseInt(binding.editSecond.text.toString())
        time = (hour * 3600 + minute * 60 + second).toDouble()
        val bundle = Bundle().apply {
            putString(BUNDLE_DEVICE_ID, device?.device_id)
            putString(BUNDLE_DEVICE_MESSAGE, "OFF")
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
