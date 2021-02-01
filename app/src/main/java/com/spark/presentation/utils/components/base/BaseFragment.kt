package com.spark.presentation.utils.components.base

import android.Manifest
import androidx.fragment.app.Fragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.spark.presentation.utils.ext.capitalizeWords
import com.spark.presentation.utils.ext.toast
import java.util.concurrent.TimeUnit

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


      fun checkPermission( permissions:MutableList<String>, onGranted: () -> Unit) {
        Dexter.withContext(requireActivity())
            .withPermissions(
                permissions
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    onGranted.invoke()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {/* ... */
                }
            }).check()
    }

}