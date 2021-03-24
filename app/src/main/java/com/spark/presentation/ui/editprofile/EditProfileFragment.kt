package com.spark.presentation.ui.editprofile

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.spark.R
import com.spark.data.utils.*
import com.spark.domain.models.SingleValueEntity
import com.spark.presentation.utils.components.base.BaseFragment
import com.spark.presentation.utils.components.base.EspressoIdlingResource
import com.spark.presentation.utils.components.base.PERMISSION_RESULT
import com.spark.presentation.utils.components.base.SingleValueAdapter
import com.spark.presentation.utils.components.bottomSheetList.base.IBaseItemListener
import com.spark.presentation.utils.ext.gone
import com.spark.presentation.utils.ext.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.edit_profile_fragment.*
import kotlinx.android.synthetic.main.edit_profile_fragment.avatar
import kotlinx.android.synthetic.main.edit_profile_fragment.avatarContainer
import kotlinx.android.synthetic.main.edit_profile_fragment.loading
import java.io.File
import com.spark.domain.models.ProfileEntity as ProfileEntity

@AndroidEntryPoint
class EditProfileFragment : BaseFragment() {

    private val viewModel: EditProfileViewModel by viewModels()

    private lateinit var newAvatarFile: File
    private lateinit var adapterGenders: SingleValueAdapter
    private lateinit var adapterReligions: SingleValueAdapter
    private lateinit var adapterMarital: SingleValueAdapter
    private lateinit var adapterLocations: SingleValueAdapter
    private val GALLERY_CODE = 1000


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireActivity())
            .inflate(R.layout.edit_profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        EspressoIdlingResource.increment()

        initLiveDataListeners()
        initAdapters()

//        genderSpinner.setSpinnerAdapter(adapterGenders)

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

        viewModel.getLocationsState.observe(viewLifecycleOwner, {
            it.onSuccess { data ->
                adapterLocations =
                    SingleValueAdapter(
                        data?.toMutableList(),
                        object : IBaseItemListener<SingleValueEntity> {
                            override fun onClick(
                                position: Int?,
                                model: SingleValueEntity?,
                                viewId: View?
                            ) {
                                locationSpinner.setText(model?.title)
                                locationSpinner.dismiss()
                            }
                        })
                locationSpinner.setSpinnerAdapter(adapterLocations)

            }.onError {
                showError(it)
            }.onNetworkError {
                showError(it)
            }

        })

        avatarContainer.setOnClickListener {
            checkPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) { result ->
                when (result) {
                    PERMISSION_RESULT.GRANTED -> openGallery()
                    PERMISSION_RESULT.DENIED -> showError(getString(R.string.spark_needs_permission))
                    PERMISSION_RESULT.RATIONAL -> showError(getString(R.string.spark_needs_permission))
                }
            }
        }

        backBtn.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_CODE) {
            val uri = data?.data
            if (uri != null && activity != null) {
                newAvatarFile = File(FilePickUtils.getPath(requireActivity(), uri))
                avatar.loadFile(newAvatarFile)
                //compress(file)
            }
        }
    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK);
        intent.type = "image/*"
        this.startActivityForResult(intent, GALLERY_CODE);
    }

    private fun initAdapters() {

        viewModel.canChooseGender.observeForever { canChoose ->
            if (canChoose) {
                adapterGenders =
                    SingleValueAdapter(
                        mutableListOf(
                            SingleValueEntity(getString(R.string.female)),
                            SingleValueEntity(getString(R.string.male))
                        ), object : IBaseItemListener<SingleValueEntity> {
                            override fun onClick(
                                position: Int?,
                                model: SingleValueEntity?,
                                viewId: View?
                            ) {
                                genderSpinner.setText(model?.title)
                                genderSpinner.dismiss()

                            }
                        })
                genderSpinner.setSpinnerAdapter(adapterGenders)

            } else {
                genderSpinner.setHint(getString(R.string.gender_cannot_set_twice))
            }
        }

    }

    private fun initLiveDataListeners() {
        viewModel.profileState.observe(viewLifecycleOwner, {
            it.onSuccess {
                showProfile(it)
            }
            it.onError { showError(it) }
        })
        viewModel.updateProfileState.observe(viewLifecycleOwner, {
            it.onSuccess {
                showProfile(it)
                showMessage(getString(R.string.profile_saved))
                Handler(Looper.getMainLooper()).postDelayed({
                    Navigation.findNavController(updateProfileBtn).navigate(
                        R.id.action_editProfileFragment_to_showProfileFragment
                    )
                }, 1500)

            }
            it.onError { showError(it) }

        })
        viewModel.uploadAvatarState.observe(viewLifecycleOwner, {
            it.onSuccess {
                showProfile(it)
                showMessage(getString(R.string.avatar_uploaded))
            }
            it.onError { showError(it) }
        })

        viewModel.loading.observe(viewLifecycleOwner, {
            updateProfileBtn.isEnabled = !it
            if (it) loading.visible() else loading.gone()
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
            maritalSpinner.setText(maritalStatus)
            occupationEdt.setText(occupation)
            aboutMeEdt.setText(aboutMe)
            locationSpinner.setText(locationTitle)
        }
        EspressoIdlingResource.decrement()
    }


    fun updateProfile() {
        viewModel.updateProfile(
            ProfileEntity(
                displayName = displayNameEdt.getText(),
                realName = realNameEdt.getText(),
                birthday = birthdayEdt.getText(),
                gender = genderSpinner.getText(),
                ethnicity = ethnicitySpinner.getText(),
                religion = religionSpinner.getText(),
                height = heightEdt.getText().getAge(),
                maritalStatus = maritalSpinner.getText(),
                occupation = occupationEdt.getText(),
                aboutMe = aboutMeEdt.getText(),
                locationTitle = locationSpinner.getText()
            )
        )

        //Call uploader, if new avatar selected from gallery
        if (::newAvatarFile.isInitialized)
            viewModel.uploadAvatar(newAvatarFile)

    }

    override fun showMessage(msg: String?) {
        super.showMessage(msg)
        errorMsgTv.text = ""
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkGenderIsEditable()
    }
    override fun showError(msg: String?) {
        super.showError(msg)
        errorMsgTv.text = msg
    }


}