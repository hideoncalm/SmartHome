package com.quyen.smarthome.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.quyen.smarthome.utils.CustomProgressBar
import javax.inject.Inject

abstract class BaseFragment<T : ViewBinding, VM : BaseViewModel> : Fragment() {

    private var _binding: T? = null
    val binding: T
        get() = _binding ?: throw IllegalStateException("Binding is not valid")
    abstract val methodInflater: (LayoutInflater, ViewGroup?, Boolean) -> T

    abstract val viewModel : VM

    @Inject
    lateinit var progressBar : CustomProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = methodInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerViewModel()
        initViews()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observerViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) {
                activity?.let { it1 -> progressBar.showProgressBar(it1) }
            } else {
                progressBar.dismissProgressBar()
            }
        })
        viewModel.message.observe(viewLifecycleOwner, {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })
    }

    abstract fun initViews()
    abstract fun initData()
}
