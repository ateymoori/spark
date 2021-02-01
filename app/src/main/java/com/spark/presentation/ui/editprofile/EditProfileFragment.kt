package com.spark.presentation.ui.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.spark.R
import com.spark.data.utils.*
import com.spark.domain.models.SingleValueEntity
import com.spark.presentation.utils.components.base.BaseFragment
import com.spark.presentation.utils.components.base.SingleValueAdapter
import com.spark.presentation.utils.components.bottomSheetList.base.IBaseItemListener
import com.spark.presentation.utils.ext.log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.edit_profile_fragment.*

@AndroidEntryPoint
class EditProfileFragment : BaseFragment() {

    private lateinit var adapterEthnicithy: SingleValueAdapter
    private lateinit var adapterGenders: SingleValueAdapter
    private lateinit var adapterReligions: SingleValueAdapter
    private val viewModel: EditProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireActivity())
            .inflate(R.layout.edit_profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initAdapters()

        gender.setSpinnerAdapter(adapterGenders )



        viewModel.ethnicities.observe(viewLifecycleOwner, {
            it.onSuccess { data ->
                adapterReligions =
                    SingleValueAdapter(data?.toMutableList(), object : IBaseItemListener<SingleValueEntity> {
                        override fun onClick(position: Int?, model: SingleValueEntity?, viewId: View?) {
                            ethnicithy.setText(model?.title)
                            ethnicithy.dismiss()
                        }
                    })
                ethnicithy.setSpinnerAdapter(adapterReligions)
            }.onError {
                showError(it)
            }.onNetworkError {
                showError(it)
            }

        })

        viewModel.religions.observe(viewLifecycleOwner, {
            it.onSuccess { data ->
                adapterReligions =
                    SingleValueAdapter(data?.toMutableList(), object : IBaseItemListener<SingleValueEntity> {
                        override fun onClick(position: Int?, model: SingleValueEntity?, viewId: View?) {
                            religion.setText(model?.title)
                            religion.dismiss()
                        }
                    })
                religion.setSpinnerAdapter(adapterReligions)

            }.onError {
                showError(it)
            }.onNetworkError {
                showError(it)
            }

        })


    }

    private fun initAdapters() {

        adapterGenders =
            SingleValueAdapter(mutableListOf(SingleValueEntity("Female"), SingleValueEntity("Male")), object : IBaseItemListener<SingleValueEntity> {
                override fun onClick(position: Int?, model: SingleValueEntity?, viewId: View?) {
                    gender.setText(model?.title)
                    gender.dismiss()
                }
            })


    }



}