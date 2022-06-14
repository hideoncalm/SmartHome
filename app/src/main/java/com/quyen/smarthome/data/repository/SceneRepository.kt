package com.quyen.smarthome.data.repository

import androidx.lifecycle.LiveData
import com.quyen.smarthome.ui.scenes.adapter.Scene

interface SceneRepository {
    fun getScenes() : LiveData<List<Scene>>
}