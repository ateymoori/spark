package com.spark.presentation.utils.components.base

import androidx.fragment.app.Fragment
import com.spark.presentation.utils.ext.capitalizeWords
import com.spark.presentation.utils.ext.toast

open class BaseFragment: Fragment() {


    fun showMessage(msg: String?){
        msg?.let {
            it.toast(requireContext())
            //AlertView(requireActivity(), "message"?.capitalizeWords(), AlertView.STATE_SUCCESS)
        }
    }
    fun showError(msg: String?){
        msg?.let {
            it.toast(requireContext())
            //AlertView(requireActivity(), "message"?.capitalizeWords(), AlertView.STATE_SUCCESS)
        }
    }
}