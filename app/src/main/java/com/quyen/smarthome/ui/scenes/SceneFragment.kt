package com.quyen.smarthome.ui.scenes

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.broadcast.AlarmReceiver
import com.quyen.smarthome.data.model.AlarmTime
import com.quyen.smarthome.data.model.Device
import com.quyen.smarthome.databinding.FragmentScencesBinding
import com.quyen.smarthome.ui.scenes.adapter.Scene
import com.quyen.smarthome.ui.scenes.adapter.SceneAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SceneFragment : BaseFragment<FragmentScencesBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentScencesBinding =
        FragmentScencesBinding::inflate

    @Inject
    lateinit var alarmManager: AlarmManager

    private val viewModel: SceneViewModel by viewModels()

    private val sceneAdapter by lazy {
        SceneAdapter(::onItemSceneClick, ::onDeleteButtonClick)
    }

    override fun initViews() {
        binding.apply {
            recyclerItem.adapter = sceneAdapter
        }

    }

    override fun initData() {
        viewModel.scenes.observe(this, {
            sceneAdapter.updateData(it as MutableList<Scene>)
        })
        viewModel.alarmsOfScene.observe(this, {
            for(alarm in it){
                viewModel.deleteScene(alarm)
                cancelAlarm(alarm)
            }
        })
    }

    private fun onItemSceneClick(scene: Scene) {
        val action = SceneFragmentDirections.actionSceneFragmentToFragmentAlarmDetail(scene)
        findNavController().navigate(action)
    }

    private fun onDeleteButtonClick(scene: Scene) {
        viewModel.getAlarmsOfScene(scene)
    }

    private fun cancelAlarm(alarmTime: AlarmTime) {
        val intent = Intent(activity, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context, alarmTime.requestCode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent)
        }
    }
}
