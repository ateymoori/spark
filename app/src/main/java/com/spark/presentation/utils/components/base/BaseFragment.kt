package com.spark.presentation.utils.components.base

import android.os.Looper
import androidx.fragment.app.Fragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.spark.presentation.utils.ext.toast
import java.util.logging.Handler

open class BaseFragment : Fragment() {

    open fun showMessage(msg: String?) {
        msg?.let {
            it.toast(requireContext())
        }
    }

    open fun showError(msg: String?) {
        msg?.let {
            it.toast(requireContext())
        }
    }

    fun checkPermission(permission: String, onGranted: (PERMISSION_RESULT) -> Unit) {
        Dexter.withContext(requireActivity())
            .withPermission(
                permission
            ).withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    onGranted.invoke(PERMISSION_RESULT.GRANTED)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    onGranted.invoke(PERMISSION_RESULT.DENIED)
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                    onGranted.invoke(PERMISSION_RESULT.RATIONAL)
                }

            }).check()
    }

}