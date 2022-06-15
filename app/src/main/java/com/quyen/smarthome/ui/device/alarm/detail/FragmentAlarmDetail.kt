package com.quyen.smarthome.ui.device.alarm.detail

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.broadcast.AlarmReceiver
import com.quyen.smarthome.data.model.AlarmTime
import com.quyen.smarthome.databinding.FragmentAlarmDetailBinding
import com.quyen.smarthome.ui.scenes.adapter.Scene
import com.quyen.smarthome.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class FragmentAlarmDetail : BaseFragment<FragmentAlarmDetailBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAlarmDetailBinding =
        FragmentAlarmDetailBinding::inflate

    private var scene: Scene? = null
    private var alarms = mutableListOf<AlarmTime>()
    private val viewModel: FragmentAlarmDetailViewModel by viewModels()

    @Inject
    lateinit var alarmManager : AlarmManager

    override fun initViews() {
        scene = arguments?.getParcelable(Constant.SCENE_KEY)
        binding.apply {
            scene?.let {
                textToolbarTitle.text = it.deviceName
                editHour.text = it.hour.toString()
                editMinute.text = it.minute.toString()
                viewModel.getDeviceById(it.deviceId)
            }
        }
        scene?.let {
            viewModel.getAlarmsOfScene(it)
        }
        binding.apply {
            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }
            buttonSaveTimer.setOnClickListener {
            }
            buttonDeleteTimer.setOnClickListener {
                for (alarm in alarms) {
                    cancelAlarm(alarm)
                    viewModel.deleteAlarm(alarm)
                }
                findNavController().popBackStack()
            }
        }
    }

    override fun initData() {
        viewModel.alarmsOfScene.observe(viewLifecycleOwner, {
            alarms = it
            for (alarm in alarms) {
                updateCheckbox(alarm)
                binding.apply {
                    radio0n.isChecked = alarm.state == Constant.STATE_ON
                    radio0ff.isChecked = alarm.state == Constant.STATE_OFF
                }
            }
        })
    }

    private fun updateCheckbox(alarm: AlarmTime) {
        when (alarm.dayOfWeek) {
            Calendar.MONDAY -> binding.checkMonday.isChecked = true
            Calendar.TUESDAY -> binding.checkTue.isChecked = true
            Calendar.THURSDAY -> binding.checkThus.isChecked = true
            Calendar.WEDNESDAY -> binding.checkWed.isChecked = true
            Calendar.FRIDAY -> binding.checkFri.isChecked = true
            Calendar.SATURDAY -> binding.checkSat.isChecked = true
            Calendar.SUNDAY -> binding.checkSun.isChecked = true
            else -> {
            }
        }
    }

    private fun setAlarm(alarmTime: AlarmTime, isRepeat: Boolean) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val bundle = Bundle().apply {
            putInt(Constant.DEVICE_STATE_KEY, alarmTime.state)
            putParcelable(Constant.DEVICE_KEY, viewModel.device)
        }
        intent.putExtra(Constant.BUNDLE_ALARM, bundle)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context, alarmTime.requestCode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        if (pendingIntent != null) {
            val cal: Calendar = GregorianCalendar().apply {
                if (alarmTime.dayOfWeek != Constant.NOT_REPEAT) {
                    add(Calendar.DAY_OF_WEEK, alarmTime.dayOfWeek)
                }
                set(Calendar.HOUR_OF_DAY, alarmTime.hour)
                set(Calendar.MINUTE, alarmTime.minute)
            }
            if (isRepeat) {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    cal.timeInMillis,
                    AlarmManager.INTERVAL_DAY * 7,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)
            }
        }
    }
    private fun cancelAlarm(alarmTime: AlarmTime) {
        val intent = Intent(activity, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context, alarmTime.requestCode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent)
        }
    }
}
