package com.spark.presentation.ui.editprofile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.data.utils.Resource
import com.spark.domain.models.ProfileEntity
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.usecases.*
import kotlinx.coroutines.launch
import java.io.File

class EditProfileViewModel @ViewModelInject constructor(
    private val getEthnicities: GetEthnicities,
    private val getReligions: GetReligions,
    private val getMaritalList: GetMaritalList,
    private val getProfile: GetProfile,
    private val updateProfile: UpdateProfile,
    private val uploadAvatar: UploadAvatar
) : ViewModel(), LifecycleObserver {

    val ethnicitiesState = MutableLiveData<Resource<List<SingleValueEntity>>>()
    val religionsState = MutableLiveData<Resource<List<SingleValueEntity>>>()
    val maritalListState = MutableLiveData<Resource<List<SingleValueEntity>>>()
    val profileState = MutableLiveData<Resource<ProfileEntity>>()
    val updateProfileState = MutableLiveData<Resource<ProfileEntity>>()
    val uploadAvatarState = MutableLiveData<Resource<ProfileEntity>>()

    init {
        getEnthnics()
        getReligions()
        getMaritalList()
        getProfile()
    }

    private fun getEnthnics() {
        viewModelScope.launch {
            ethnicitiesState.postValue(getEthnicities.invoke())
        }
    }

    private fun getReligions() {
        viewModelScope.launch {
            religionsState.postValue(getReligions.invoke())
        }
    }

    private fun getMaritalList() {
        viewModelScope.launch {
            maritalListState.postValue(getMaritalList.invoke())
        }
    }

    private fun getProfile() {
        viewModelScope.launch {
            profileState.postValue(getProfile.invoke())
        }
    }

    fun saveProfile(profile: ProfileEntity?) {
        viewModelScope.launch {
            updateProfileState.postValue(updateProfile.invoke(profile))
        }
    }

    fun uploadAvatar(avatar: File?) {
        avatar?.let {
            viewModelScope.launch {
                uploadAvatarState.postValue(uploadAvatar.invoke(avatar))
            }
        }
    }

}