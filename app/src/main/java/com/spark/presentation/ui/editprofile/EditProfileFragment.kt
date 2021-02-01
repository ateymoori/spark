package com.spark.presentation.ui.editprofile

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.spark.R
import com.spark.data.utils.*
import com.spark.domain.models.SingleValueEntity
import com.spark.presentation.utils.components.base.BaseFragment
import com.spark.presentation.utils.components.base.SingleValueAdapter
import com.spark.presentation.utils.components.bottomSheetList.base.IBaseItemListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.edit_profile_fragment.*
import java.io.File

@AndroidEntryPoint
class EditProfileFragment : BaseFragment() {

    private lateinit var adapterGenders: SingleValueAdapter
    private lateinit var adapterReligions: SingleValueAdapter
    private val viewModel: EditProfileViewModel by viewModels()
    private val GALLERY_CODE = 1000

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

        gender.setSpinnerAdapter(adapterGenders)



        viewModel.ethnicities.observe(viewLifecycleOwner, {
            it.onSuccess { data ->
                adapterReligions =
                    SingleValueAdapter(
                        data?.toMutableList(),
                        object : IBaseItemListener<SingleValueEntity> {
                            override fun onClick(
                                position: Int?,
                                model: SingleValueEntity?,
                                viewId: View?
                            ) {
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
                    SingleValueAdapter(
                        data?.toMutableList(),
                        object : IBaseItemListener<SingleValueEntity> {
                            override fun onClick(
                                position: Int?,
                                model: SingleValueEntity?,
                                viewId: View?
                            ) {
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

        avatarContainer.setOnClickListener {
            checkPermission(
                mutableListOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                openGallery()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_CODE) {
            val uri = data?.data
            if (uri != null && activity != null) {
                val file = File(FilePickUtils.getPath(requireActivity(), uri))
                 avatar.loadFile(file)
                //compressAndUpload(file)
            }
        }
    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK);
        intent.type = "image/*"
        this.startActivityForResult(intent, GALLERY_CODE);
    }

    private fun initAdapters() {

        adapterGenders =
            SingleValueAdapter(
                mutableListOf(
                    SingleValueEntity("Female"),
                    SingleValueEntity("Male")
                ), object : IBaseItemListener<SingleValueEntity> {
                    override fun onClick(position: Int?, model: SingleValueEntity?, viewId: View?) {
                        gender.setText(model?.title)
                        gender.dismiss()
                    }
                })


    }


}