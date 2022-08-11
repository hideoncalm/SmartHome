package com.quyen.smarthome.ui.room.createroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.data.model.Room
import com.quyen.smarthome.databinding.FragmentCreateRoomBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentCreateRoom : BaseFragment<FragmentCreateRoomBinding, FragmentCreateRoomViewModel>(){

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCreateRoomBinding
        = FragmentCreateRoomBinding::inflate

    override val viewModel: FragmentCreateRoomViewModel by viewModels()


    override fun initViews() {

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonCreateHome.setOnClickListener {
            val roomName = binding.textHomeName.text.toString()
            val room = Room(System.currentTimeMillis().toString(), roomName)
            viewModel.insertRoom(room)
            findNavController().popBackStack()
        }
    }

    override fun initData() {
    }
}