package com.quyen.smarthome.ui.device.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.broadcast.AlarmReceiver
import com.quyen.smarthome.data.model.AlarmTime
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.databinding.FragmentSelectAlarmBinding
import com.quyen.smarthome.utils.Constant
import com.quyen.smarthome.utils.Constant.BUNDLE_ALARM
import com.quyen.smarthome.utils.Constant.DEVICE_KEY
import com.quyen.smarthome.utils.Constant.DEVICE_STATE_KEY
import com.quyen.smarthome.utils.Constant.FRI
import com.quyen.smarthome.utils.Constant.MONDAY
import com.quyen.smarthome.utils.Constant.NOT_REPEAT
import com.quyen.smarthome.utils.Constant.REPEAT
import com.quyen.smarthome.utils.Constant.SAT
import com.quyen.smarthome.utils.Constant.SOURCE_DIRECTION
import com.quyen.smarthome.utils.Constant.STATUS_OFF
import com.quyen.smarthome.utils.Constant.STATUS_ON
import com.quyen.smarthome.utils.Constant.SUN
import com.quyen.smarthome.utils.Constant.THUS
import com.quyen.smarthome.utils.Constant.TUE
import com.quyen.smarthome.utils.Constant.WED
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class FragmentAlarmDevice : BaseFragment<FragmentSelectAlarmBinding>() {

    private var device: Device? = null
    private var srcDirection: String = ""
    private var alarm: AlarmTime? = null
    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSelectAlarmBinding =
        FragmentSelectAlarmBinding::inflate

    @Inject
    lateinit var alarmManager: AlarmManager

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
                alarm?.let { it1 -> cancelAlarm(it1) }
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

    private fun setupAlarm() {
        val hour = Integer.parseInt(binding.editHour.text.toString())
        val minute = Integer.parseInt(binding.editMinute.text.toString())
        val mon = if (binding.checkMonday.isChecked) REPEAT else NOT_REPEAT
        val tus = if (binding.checkTue.isChecked) REPEAT else NOT_REPEAT
        val wed = if (binding.checkWed.isChecked) REPEAT else NOT_REPEAT
        val thus = if (binding.checkThus.isChecked) REPEAT else NOT_REPEAT
        val fri = if (binding.checkFri.isChecked) REPEAT else NOT_REPEAT
        val sat = if (binding.checkSat.isChecked) REPEAT else NOT_REPEAT
        val sun = if (binding.checkSun.isChecked) REPEAT else NOT_REPEAT
        val state = if (binding.radio0n.isChecked) STATUS_ON else STATUS_OFF

        device?.let {
            if (mon == NOT_REPEAT && tus == NOT_REPEAT && wed == NOT_REPEAT && thus == NOT_REPEAT && fri == NOT_REPEAT && sat == NOT_REPEAT && sun == NOT_REPEAT) {
                val alarmTime = AlarmTime(it.device_id, hour, minute, state)
                setAlarm(alarmTime, false)
            }
            if (mon == REPEAT) {
                val alarmTime = AlarmTime(it.device_id, hour, minute, state, Calendar.MONDAY)
                setAlarm(alarmTime, true)
                viewModel.insertAlarm(alarmTime)
            }
            if (tus == REPEAT) {
                val alarmTime = AlarmTime(it.device_id, hour, minute, state, Calendar.TUESDAY)
                setAlarm(alarmTime, true)
                viewModel.insertAlarm(alarmTime)
            }
            if (wed == REPEAT) {
                val alarmTime = AlarmTime(it.device_id, hour, minute, state, Calendar.WEDNESDAY)
                setAlarm(alarmTime, true)
                viewModel.insertAlarm(alarmTime)
            }
            if (thus == REPEAT) {
                val alarmTime = AlarmTime(it.device_id, hour, minute, state, Calendar.THURSDAY)
                setAlarm(alarmTime, true)
                viewModel.insertAlarm(alarmTime)
            }
            if (fri == REPEAT) {
                val alarmTime = AlarmTime(it.device_id, hour, minute, state, Calendar.FRIDAY)
                setAlarm(alarmTime, true)
                viewModel.insertAlarm(alarmTime)
            }
            if (sat == REPEAT) {
                val alarmTime = AlarmTime(it.device_id, hour, minute, state, Calendar.SATURDAY)
                setAlarm(alarmTime, true)
                viewModel.insertAlarm(alarmTime)
            }
            if (sun == REPEAT) {
                val alarmTime = AlarmTime(it.device_id, hour, minute, state, Calendar.SUNDAY)
                setAlarm(alarmTime, true)
                viewModel.insertAlarm(alarmTime)
            }
        }
    }


    private fun setAlarm(alarmTime: AlarmTime, isRepeat: Boolean) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val bundle = Bundle().apply {
            putInt(DEVICE_STATE_KEY, alarmTime.state)
            putParcelable(DEVICE_KEY, device)
        }
        intent.putExtra(BUNDLE_ALARM, bundle)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context, alarmTime.requestCode, intent,
                FLAG_UPDATE_CURRENT
            )
        if (pendingIntent != null && alarmManager != null) {
            val cal: Calendar = GregorianCalendar().apply {
                if (alarmTime.dayOfWeek != NOT_REPEAT) {
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
                FLAG_UPDATE_CURRENT
            )
        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent)
        }
    }
}