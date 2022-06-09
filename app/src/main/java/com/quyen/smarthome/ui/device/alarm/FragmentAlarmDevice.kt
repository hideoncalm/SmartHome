package com.quyen.smarthome.ui.device.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.broadcast.AlarmReceiver
import com.quyen.smarthome.data.model.AlarmTime
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.databinding.FragmentSelectAlarmBinding
import com.quyen.smarthome.utils.Constant
import com.quyen.smarthome.utils.Constant.DEVICE_KEY
import com.quyen.smarthome.utils.Constant.DEVICE_STATE_KEY
import com.quyen.smarthome.utils.Constant.SOURCE_DIRECTION
import java.util.*
import java.util.Calendar.MONDAY

class FragmentAlarmDevice : BaseFragment<FragmentSelectAlarmBinding>() {

    private var device: Device? = null
    private var srcDirection: String = ""
    private var alarm: AlarmTime? = null
    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSelectAlarmBinding =
        FragmentSelectAlarmBinding::inflate

    private val viewModel: FragmentAddAlarmViewModel by viewModels()

    override fun initViews() {
        binding.apply {
            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }
            buttonSaveTimer.setOnClickListener {
                setupAlarm()
            }
            buttonDeleteTimer.setOnClickListener {
                cancelAlarm()
            }
        }
    }

    override fun initData() {
        device = arguments?.getParcelable(Constant.DEVICE_KEY)
        alarm = arguments?.getParcelable(Constant.ALARM_KEY)
        srcDirection = arguments?.getString(SOURCE_DIRECTION).toString()
        binding.textToolbarTitle.text = device!!.device_name

        viewModel.result.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().popBackStack()
            }
        })
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun setupAlarm() {
        val hour = Integer.parseInt(binding.editHour.text.toString())
        val minute = Integer.parseInt(binding.editMinute.text.toString())
        val mon = if (binding.checkMonday.isChecked) 1 else 0
        val tus = if (binding.checkTue.isChecked) 1 else 0
        val wed = if (binding.checkWed.isChecked) 1 else 0
        val thus = if (binding.checkThus.isChecked) 1 else 0
        val fri = if (binding.checkFri.isChecked) 1 else 0
        val sat = if (binding.checkSat.isChecked) 1 else 0
        val sun = if (binding.checkSun.isChecked) 1 else 0
        val state = if(binding.radioGroup.checkedRadioButtonId == 0) 1 else 0
        val alarm = AlarmTime(hour, minute, mon, tus, wed, thus, fri, sat, sun, "", state)

        val alarmManager =
            requireContext().getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(activity, AlarmReceiver::class.java)
        val bundle = Bundle().apply {
            putInt(DEVICE_STATE_KEY, alarm.state)
            putParcelable(DEVICE_KEY, device)
        }
        intent.putExtras(bundle)
        val pendingIntent =
            PendingIntent.getService(
                context, alarm.requestCode, intent,
                FLAG_UPDATE_CURRENT
            )
        if (pendingIntent != null && alarmManager != null) {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DAY_OF_WEEK, MONDAY)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun cancelAlarm() {
        alarm?.let {
            val alarmManager =
                requireContext().getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            val intent = Intent(activity, AlarmReceiver::class.java)
            val pendingIntent =
                PendingIntent.getService(
                    context, it.requestCode, intent,
                    FLAG_UPDATE_CURRENT
                )
            if (pendingIntent != null && alarmManager != null) {
                alarmManager.cancel(pendingIntent)
            }
        }
    }
}
