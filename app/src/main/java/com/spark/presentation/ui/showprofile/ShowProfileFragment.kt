package com.spark.presentation.ui.showprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.spark.R
import com.spark.data.utils.GsonUtils.toStringByGson
import com.spark.data.utils.Resource
import com.spark.data.utils.loadUrl
import com.spark.data.utils.log
import com.spark.domain.models.ProfileEntity
import com.spark.presentation.ui.editprofile.EditProfileViewModel
import com.spark.presentation.utils.components.base.BaseFragment
import com.spark.presentation.utils.ext.gone
import com.spark.presentation.utils.ext.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_show_profile.*

@AndroidEntryPoint
class ShowProfileFragment : BaseFragment() {

    private val viewModel: ShowProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.profileState.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> showProfile(it.data)
                is Resource.Loading -> loading.visible()
                is Resource.Failure.Generic -> TODO()
                is Resource.Failure.NetworkException -> TODO()
                is Resource.Failure.UnAuthorized -> TODO()
            }
        })
    }

    private fun showProfile(profile: ProfileEntity?) {
        loading.gone()
        profile?.apply {
            avatar.loadUrl(picture)
            displayNameTv.setText(displayName)
            realNameTv.setText(realName)
            birthdayTv.setText(birthday)
            genderTv.setText(gender)
            ethnicityTv.setText(ethnicity)
            religionTv.setText(religion)
            heightTv.setText(height.toString())
            maritalTv.setText(maritalStatus)
            occupationTV.setText(occupation)
            locationTv.setText(locationTitle)
        }
    }

}