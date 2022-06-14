package com.quyen.smarthome.data.repository.imp

import androidx.lifecycle.LiveData
import com.quyen.smarthome.data.repository.SceneRepository
import com.quyen.smarthome.data.source.local.TimeDao
import com.quyen.smarthome.ui.scenes.adapter.Scene
import javax.inject.Inject

class SceneRepositoryImp @Inject constructor(
    private val timeDao: TimeDao
) : SceneRepository {
    override fun getScenes(): LiveData<List<Scene>> = timeDao.getScenes()
}