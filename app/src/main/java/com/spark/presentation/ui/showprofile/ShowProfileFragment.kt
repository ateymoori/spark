package com.spark.presentation.ui.showprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.spark.R
import com.spark.data.utils.Resource
import com.spark.data.utils.loadUrl
import com.spark.data.utils.log
import com.spark.domain.models.ProfileEntity
import com.spark.domain.models.SingleValueEntity
import com.spark.presentation.ui.showprofile.adapter.ProfileDetailsAdapter
import com.spark.presentation.utils.components.base.BaseFragment
import com.spark.presentation.utils.components.base.EspressoIdlingResource
import com.spark.presentation.utils.ext.add
import com.spark.presentation.utils.ext.gone
import com.spark.presentation.utils.ext.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_show_profile.*
import kotlinx.android.synthetic.main.fragment_show_profile.avatar
import kotlinx.android.synthetic.main.fragment_show_profile.loading

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

        EspressoIdlingResource.increment()

        viewModel.profileState.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    showLoading(false)
                    showProfile(it.data)
                }
                is Resource.Loading -> showLoading(true)
                is Resource.Failure.Generic -> {
                    showLoading(false)
                    showError(it.error)
                }
                is Resource.Failure.NetworkException -> {
                    showLoading(false)
                    showError(it.error)
                }
                is Resource.Failure.UnAuthorized -> {
                    showLoading(false)
                    showError(it.error)
                }
            }
        })

        editBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(
                R.id.action_showProfileFragment_to_editProfileFragment
            )
        }
    }

    private fun showProfile(profile: ProfileEntity?) {
        profile?.apply {
            avatar.loadUrl(picture)
            lastUpdate.text = getString(R.string.last_update).add(" ").add(profile.updatedAt)
            detailsLv.adapter = ProfileDetailsAdapter(mapProfileList(profile), null)
        }
        EspressoIdlingResource.decrement()
    }

    private fun showLoading(show: Boolean) = if (show) loading?.visible() else loading.gone()

    override fun onResume() {
        super.onResume()
        viewModel.getProfile()
    }

    private fun mapProfileList(profile: ProfileEntity?): MutableList<SingleValueEntity> {
        with(profile) {
            return mutableListOf(
                SingleValueEntity(
                    getString(R.string.display_name),
                    this?.displayName
                ),
                SingleValueEntity(
                    getString(R.string.real_name),
                    this?.realName
                ),
                SingleValueEntity(
                    getString(R.string.gender),
                    this?.gender
                ),
                SingleValueEntity(
                    getString(R.string.marital_status),
                    this?.maritalStatus
                ),
                SingleValueEntity(
                    getString(R.string.birthday),
                    this?.birthday
                ),
                SingleValueEntity(
                    getString(R.string.ethnicity),
                    this?.ethnicity
                ),
                SingleValueEntity(
                    getString(R.string.religion),
                    this?.religion
                ),
                SingleValueEntity(
                    getString(R.string.height),
                    this?.height.toString().add("cm")
                ),
                SingleValueEntity(
                    getString(R.string.occupation),
                    this?.occupation
                ),
                SingleValueEntity(
                    getString(R.string.location),
                    this?.locationTitle
                )
            )
        }
    }


}