package com.quyen.smarthome.ui.addhouse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quyen.smarthome.R
import com.quyen.smarthome.base.BaseFragment
import com.quyen.smarthome.data.model.Home
import com.quyen.smarthome.databinding.FragmentHouseBinding
import com.quyen.smarthome.ui.addhouse.adapter.HouseAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAddHouse : BaseFragment<FragmentHouseBinding>() {

    override val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHouseBinding =
        FragmentHouseBinding::inflate

    private val houseAdapter: HouseAdapter by lazy {
        HouseAdapter(::onItemHouseClick)
    }
    private val viewModel: FragmentAddHouseViewModel by viewModels()

    override fun initViews() {
        binding.recyclerHome.adapter = houseAdapter
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.buttonAddHome.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentAddHouse_to_fragmentCreateHome)
        }
    }

    override fun initData() {
        viewModel.houses.observe(viewLifecycleOwner, {
            houseAdapter.updateData(it as MutableList<Home>)
        })
    }

    private fun onItemHouseClick(house: Home) {
        // go to house detail fragment
        val action = FragmentAddHouseDirections.actionFragmentAddHouseToHomeFragment(house)
        findNavController().navigate(action)
    }
}
