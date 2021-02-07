package com.spark.presentation.ui.editprofile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.spark.data.utils.Resource
import com.spark.domain.models.ProfileEntity
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.usecases.*
import com.spark.presentation.utils.components.base.BaseViewModel
import com.spark.presentation.utils.components.base.EspressoIdlingResource
import com.spark.presentation.utils.components.base.SingleLiveEvent
import kotlinx.coroutines.launch
import java.io.File

class EditProfileViewModel @ViewModelInject constructor(
    private val getEthnicities: GetEthnicities,
    private val getReligions: GetReligions,
    private val getMaritalList: GetMaritalList,
    private val getProfile: GetProfile,
    private val updateProfile: UpdateProfile,
    private val uploadAvatar: UploadAvatar
) : BaseViewModel(), LifecycleObserver {

    val ethnicitiesState = MutableLiveData<Resource<List<SingleValueEntity>>>()
    val religionsState = MutableLiveData<Resource<List<SingleValueEntity>>>()
    val maritalListState = MutableLiveData<Resource<List<SingleValueEntity>>>()
    val profileState = MutableLiveData<Resource<ProfileEntity>>()
    val updateProfileState = SingleLiveEvent<Resource<ProfileEntity>>()
    val uploadAvatarState = SingleLiveEvent<Resource<ProfileEntity>>()

    init {
         getData()
    }

    fun getData() {
        getEnthnics()
        getReligions()
        getMaritalList()
        getProfile()
    }

    fun getEnthnics() {
        viewModelScope.launch {
            ethnicitiesState.postValue(getEthnicities.invoke())
        }
    }

    fun getReligions() {
        viewModelScope.launch {
            religionsState.postValue(getReligions.invoke())
        }
    }

    fun getMaritalList() {
        viewModelScope.launch {
            maritalListState.postValue(getMaritalList.invoke())
        }
    }

    fun getProfile() {
        viewModelScope.launch {
            profileState.postValue(Resource.Loading(""))
            profileState.postValue(getProfile.invoke())
        }
    }

    fun updateProfile(profile: ProfileEntity?) {
        viewModelScope.launch {
            updateProfileState.postValue(updateProfile.invoke(profile))
        }
    }

    fun uploadAvatar(avatar: File?) {
        viewModelScope.launch {
            if (avatar == null) {
                uploadAvatarState.postValue(Resource.Failure.Generic(""))
            }
            uploadAvatarState.postValue(uploadAvatar.invoke(avatar))
        }
    }

}