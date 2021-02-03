package com.spark.presentation.ui.editprofile

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.spark.R
import com.spark.data.utils.*
import com.spark.domain.models.SingleValueEntity
import com.spark.presentation.utils.components.base.BaseFragment
import com.spark.presentation.utils.components.base.SingleValueAdapter
import com.spark.presentation.utils.components.bottomSheetList.base.IBaseItemListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.edit_profile_fragment.*
import java.io.File
import com.spark.domain.models.ProfileEntity as ProfileEntity

@AndroidEntryPoint
class EditProfileFragment : BaseFragment() {

    private lateinit var newAvatarFile: File
    private lateinit var adapterGenders: SingleValueAdapter
    private lateinit var adapterReligions: SingleValueAdapter
    private lateinit var adapterMarital: SingleValueAdapter

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

        initLiveDataListeners()
        initAdapters()

        genderSpinner.setSpinnerAdapter(adapterGenders)

        updateProfileBtn.setOnClickListener {
            updateProfile()
        }


        viewModel.ethnicitiesState.observe(viewLifecycleOwner, {
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
                                ethnicitySpinner.setText(model?.title)
                                ethnicitySpinner.dismiss()
                            }
                        })
                ethnicitySpinner.setSpinnerAdapter(adapterReligions)
            }.onError {
                showError(it)
            }.onNetworkError {
                showError(it)
            }

        })

        viewModel.religionsState.observe(viewLifecycleOwner, {
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
                                religionSpinner.setText(model?.title)
                                religionSpinner.dismiss()
                            }
                        })
                religionSpinner.setSpinnerAdapter(adapterReligions)

            }.onError {
                showError(it)
            }.onNetworkError {
                showError(it)
            }

        })

        viewModel.maritalListState.observe(viewLifecycleOwner, {
            it.onSuccess { data ->
                adapterMarital =
                    SingleValueAdapter(
                        data?.toMutableList(),
                        object : IBaseItemListener<SingleValueEntity> {
                            override fun onClick(
                                position: Int?,
                                model: SingleValueEntity?,
                                viewId: View?
                            ) {
                                maritalSpinner.setText(model?.title)
                                maritalSpinner.dismiss()
                            }
                        })
                maritalSpinner.setSpinnerAdapter(adapterMarital)

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
                newAvatarFile = File(FilePickUtils.getPath(requireActivity(), uri))
                avatar.loadFile(newAvatarFile)
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
                        genderSpinner.setText(model?.title)
                        genderSpinner.dismiss()
                    }
                })


    }

    private fun initLiveDataListeners() {
        viewModel.profileState.observe(viewLifecycleOwner, {
            it.onSuccess { showProfile(it) }
            it.onError { showError(it) }
        })
        viewModel.updateProfileState.observe(viewLifecycleOwner, {
            it.onSuccess { showMessage("Profile Saved") }
            it.onError { showError(it) }
            Navigation.findNavController(updateProfileBtn).navigate(
                R.id.action_editProfileFragment_to_showProfileFragment
            )
        })
        viewModel.uploadAvatarState.observe(viewLifecycleOwner, {
            it.onSuccess { showMessage("Avatar Uploaded") }
            it.onError { showError(it) }
        })


    }

    private fun showProfile(profile: ProfileEntity?) {
        profile?.apply {
            avatar.loadUrl(picture)
            displayNameEdt.setText(displayName)
            realNameEdt.setText(realName)
            birthdayEdt.setText(birthday)
            genderSpinner.setText(gender)
            ethnicitySpinner.setText(ethnicity)
            religionSpinner.setText(religion)
            heightEdt.setText(height.toString())
            figureSpinner.setText(figure)
            maritalSpinner.setText(maritalStatus)
            occupationEdt.setText(occupation)
            aboutMeEdt.setText(aboutMe)
            locationEdt.setText(locationTitle)
        }
    }


    private fun updateProfile() {
        viewModel.saveProfile(
            ProfileEntity(
                displayName = displayNameEdt.getText(),
                realName = realNameEdt.getText(),
                birthday = birthdayEdt.getText(),
                gender = genderSpinner.getText(),
                ethnicity = ethnicitySpinner.getText(),
                religion = religionSpinner.getText(),
                figure = figureSpinner.getText(),
                height = heightEdt.getText().toInt(),
                maritalStatus = maritalSpinner.getText(),
                occupation = occupationEdt.getText(),
                aboutMe = aboutMeEdt.getText(),
                locationTitle = locationEdt.getText(),
                latitude = null,
                longitude = null,
                updatedAt = null
            )
        )

        //Call uploader, if new avatar selected from gallery
        if (::newAvatarFile.isInitialized)
            viewModel.uploadAvatar(newAvatarFile)

    }


}